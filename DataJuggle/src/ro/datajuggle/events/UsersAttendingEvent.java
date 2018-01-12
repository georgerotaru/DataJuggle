/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.datajuggle.events;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.Event;
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
public class UsersAttendingEvent {
    
private String username = "events";
private String passwd = "events";
private String host = "jdbc:derby://localhost:1527/events;create=true";
private String driver = "org.apache.derby.jdbc.ClientDriver";
private Connection connection = null;
private Statement statement = null;
private ResultSet resultSet = null;    

    public UsersAttendingEvent() throws SQLException, ClassNotFoundException {
        FacebookClient fbClient = new DefaultFacebookClient(new AccessToken().getAccessToken(), Version.VERSION_2_11);
        
        try {
            Class driverClass = Class.forName(driver);
            connection = DriverManager.getConnection(host, username, passwd);
            connection.setAutoCommit(false);

            System.out.println("Connection to Java DB established!");

            statement = connection.createStatement();           
            resultSet = statement.executeQuery("SELECT ID FROM APP.EVENTS_ABOUT");

            List eventsList = new LinkedList();
            while (resultSet.next()) {
                String item = resultSet.getString(1);
                eventsList.add(item);
            }
            
            String currentId;
            String tableName = null;
            String searchCriteria = null;
            String userId = null;
            String userName = null;   
            String userUrl = null;

            for (int c=0; c<eventsList.size(); c++) {
                currentId = eventsList.get(c).toString();
                System.out.println("extracted current event ID: "+currentId);
                tableName = "ATTENDING_"+currentId;
                searchCriteria = currentId+"/attending";

                com.restfb.Connection<Event> eventAttending = fbClient.fetchConnection(searchCriteria, Event.class);
                System.out.println("connected to event "+currentId);

                
                statement.execute("create table "+ tableName + " (user_id varchar(25) primary key, user_name varchar(100), user_url varchar(100), event_id varchar(20))");
                System.out.println("created table "+tableName);

                userUrl = "https://www.facebook.com/"+userId;

                if (eventAttending.getData().size()!=0) {
                    for (List<Event> attendingPage : eventAttending) {
                        for (Event attending : attendingPage) {
                            userId = attending.getId();
                            System.out.println("userID attending event: " + userId);
                            userName = attending.getName().replaceAll("'", "");
                            System.out.println("username: " + userName);
                            statement.execute("INSERT INTO "+tableName+" values ('"+userId+"', '"+userName+"', '"+userUrl+"', '"+currentId+"')");
                            connection.commit();
                        }
                      }
                } else {
                    System.out.println("Nobody is attending the event "+ currentId);
                }
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
