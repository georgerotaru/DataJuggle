/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.datajuggle.groups;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.Group;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import ro.datajuggle.util.AccessToken;

/**
 *
 * @author admin
 */
public class GroupMembers {
    
private String username = "events";
private String passwd = "events";
private String host = "jdbc:derby://localhost:1527/events;create=true";
private String driver = "org.apache.derby.jdbc.ClientDriver";
private Connection connection = null;
private Statement statement = null;
private ResultSet resultSet = null;    

private String currentId;
private String searchCriteria = null;
private String userId = null;
private String userName = null;   
private String userUrl = null;
            
    public GroupMembers() throws SQLException, ClassNotFoundException {
        FacebookClient fbClient = new DefaultFacebookClient(new AccessToken().getAccessToken(), Version.VERSION_2_11);
        
        try {
            Class driverClass = Class.forName(driver);
            connection = DriverManager.getConnection(host, username, passwd);
            connection.setAutoCommit(false);

            System.out.println("Connection to Java DB established!");

            statement = connection.createStatement();           
            resultSet = statement.executeQuery("SELECT ID FROM APP.GROUPS");

            List groupList = new LinkedList();
            while (resultSet.next()) {
                String item = resultSet.getString(1);
                groupList.add(item);
            }
            


            for (int c=0; c<groupList.size(); c++) {
                int counter = 0;
                currentId = groupList.get(c).toString();
                searchCriteria = currentId+"/members";

                com.restfb.Connection<Group> groupMembers = fbClient.fetchConnection(searchCriteria, Group.class);
                System.out.println("    connected to group "+currentId);

                if (groupMembers.getData().size()!=0) {
                    for (List<Group> membersPage : groupMembers) {
                        for (Group member : membersPage) {
                            userId = member.getId();
                            userUrl = "https://www.facebook.com/"+userId;
                            userName = member.getName().replaceAll("'", "");
                            statement.execute("INSERT INTO APP.GROUP_MEMBERS (USER_ID, USER_NAME, USER_URL, GROUP_ID) values ('"+userId+"', '"+userName+"', '"+userUrl+"', '"+currentId+"')");
                            connection.commit();
                            counter++;
                        }
                      }
                } else {
                    System.out.println("No members for "+ currentId);
                }
                System.out.println("        Group "+currentId+" has "+counter+" members.");
            }
                
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception ex) { 
                    ex.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception ex) { 
                    ex.printStackTrace();
                }
            }	
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ex) { 
                    ex.printStackTrace();
                }
            }
        }
    }
}
