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
public class EventAdmins {
    
private String username = "events";
private String passwd = "events";
private String host = "jdbc:derby://localhost:1527/events;create=true";
private String driver = "org.apache.derby.jdbc.ClientDriver";
private Connection connection = null;
private Statement statement = null;
private ResultSet resultSet = null;

    public EventAdmins() throws SQLException {
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

            String currentId = null;
            String tableName = null;
            String searchCriteria = null;
            String adminId = null;
            String adminName = null;
            String userUrl = null;


            for (int c=0; c<eventsList.size(); c++) {
                int counter = 0;
                currentId = eventsList.get(c).toString();
                //System.out.println("current event ID: "+currentId);
                searchCriteria = currentId+"/admins";
                //System.out.println("search criteria "+searchCriteria);

                com.restfb.Connection<Event> eventAdmins = fbClient.fetchConnection(searchCriteria, Event.class);
                System.out.println("connected to event "+searchCriteria);

                for (int i=0; i<eventAdmins.getData().size(); i++) {
                    try {
                        adminId = eventAdmins.getData().get(i).getId();
                        //System.out.println("admin ID: "+adminId);
                        userUrl = "https://www.facebook.com/"+adminId;
                        adminName = eventAdmins.getData().get(i).getName().replaceAll("'", "\\'");
                        //System.out.println("admin name: "+adminName);
                        statement.execute("INSERT INTO APP.EVENTS_ADMIN (USER_ID, USER_NAME, USER_URL, EVENT_ID) values ('"+adminId+"', '"+adminName+"', '"+userUrl+"', '"+currentId+"')");
                        connection.commit();
                        counter++;
                    } catch (NullPointerException e) {
                        System.out.println("admin ID: "+adminId);
                        System.out.println("admin name: "+adminName);
                        e.printStackTrace();
                    }
                }
                System.out.println("The event "+currentId+" has "+counter+" admins.");
            }

            connection.setAutoCommit(true);
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
