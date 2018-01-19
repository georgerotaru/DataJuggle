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
import java.util.Scanner;
import ro.datajuggle.util.AccessToken;

/**
 *
 * @author admin
 */
public class AddGroup {
    
private String username = "events";
private String passwd = "events";
private String host = "jdbc:derby://localhost:1527/events;create=true";
private String driver = "org.apache.derby.jdbc.ClientDriver";
private Connection connection = null;
private Statement statement = null;
private ResultSet resultSet = null;

private String groupId = null;
private String groupName = null;
private String groupPrivacy = null;
            
    public AddGroup() throws SQLException {

         try {
            Class driverClass = Class.forName(driver);
            connection = DriverManager.getConnection(host, username, passwd);
            connection.setAutoCommit(false);

            System.out.println("Connection to Java DB established!");
             
            statement = connection.createStatement();           
            
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input group id: ");
            groupId = scanner.nextLine();
            
            FacebookClient fbClient = new DefaultFacebookClient(new AccessToken().getAccessToken(), Version.VERSION_2_11);
            Group groupSearch = fbClient.fetchObject(groupId, Group.class);
            groupName = groupSearch.getName().replaceAll("'", "");
            groupPrivacy = groupSearch.getPrivacy();
            
            statement.execute("INSERT INTO APP.GROUPS VALUES ('"+groupId+"', '"+groupName+"', '"+groupPrivacy+"')");
            connection.commit();
            connection.setAutoCommit(true);
            
            System.out.println("Group \""+groupName+"\" added.");
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
