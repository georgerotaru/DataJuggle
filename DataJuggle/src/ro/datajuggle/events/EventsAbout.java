/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.datajuggle.events;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Event;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import ro.datajuggle.util.AccessToken;
import ro.datajuggle.util.DateToCalendar;
import java.sql.SQLDataException;

/**
 *
 * @author admin
 */
public class EventsAbout {
    
private String username = "events";
private String passwd = "events";
private String host = "jdbc:derby://localhost:1527/events;create=true";
private String driver = "org.apache.derby.jdbc.ClientDriver";
private Connection connection = null;
private Statement statement = null;
private ResultSet resultSet = null;
private String eventId = null;
private String eventName = null;

    public EventsAbout() throws SQLException, ClassNotFoundException, NullPointerException, SQLDataException {
        try {
            Class driverClass = Class.forName(driver);
            connection = DriverManager.getConnection(host, username, passwd);
            statement = connection.createStatement();
            System.out.println("Connection to Java DB established!");
            DateToCalendar dtc = new DateToCalendar();
            FacebookClient fbClient = new DefaultFacebookClient(new AccessToken().getAccessToken(), Version.VERSION_2_11);
            String eventId;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input event id: ");
            eventId = scanner.nextLine();

            Event eventSearch = fbClient.fetchObject(eventId, Event.class);
            Event eventSearchWithParam = fbClient.fetchObject(eventId, Event.class, Parameter.with("fields", "attending_count,interested_count"));
            
            try {
                eventName = eventSearch.getName();
                System.out.println("name: "+eventName);
            } catch (NullPointerException e) {
                System.out.println("name: "+eventName);
            }

            String eventCity = null;
            try {
                eventCity = eventSearch.getPlace().getLocation().getCity();
                System.out.println("city: "+eventCity);
            } catch (NullPointerException e) {
                System.out.println("city: "+eventCity);
            }

            String eventPlace = null;
            try {
                eventPlace = eventSearch.getPlace().getName().replaceAll("'", " ");
                System.out.println("place: "+eventPlace);
            } catch (NullPointerException e) {
                System.out.println("place: "+eventPlace);
            }

            DateFormat romanianDate = new SimpleDateFormat("dd.MM.yyyy"); 
            DateFormat shortTime = new SimpleDateFormat("HH:mm:ss");

            String eventStartDate = null;
            String eventStartTime = null;
            Date eventStart = null;
            try {
                eventStart = eventSearch.getStartTime();
                eventStartDate = romanianDate.format(eventStart);
                System.out.println("start_date: "+eventStartDate);
                eventStartTime = shortTime.format(eventStart);
                System.out.println("start_time: "+eventStartTime);
            } catch (NullPointerException e) {
                eventStartDate = "01.01.1900";
                eventStartTime = "00:00:00";                
                System.out.println("start_date: "+eventStartDate);
                System.out.println("start_time: "+eventStartTime);
            }

            String eventEndDate = null;
            String eventEndTime = null;
            Date eventEnd = null;
            try {
                eventEnd = eventSearch.getEndTime();
                eventEndDate = romanianDate.format(eventEnd);
                System.out.println("end_date: "+eventEndDate);
                eventEndTime = shortTime.format(eventEnd);
                System.out.println("end_time: "+eventEndTime);
            } catch (NullPointerException e) {
                eventEndDate = "01.01.2000";
                eventEndTime = "00:00:00";
                System.out.println("end_date: "+eventEndDate);
                System.out.println("end_time: "+eventEndTime);
            }

            Integer eventAttending = null;
            try {
                eventAttending = eventSearchWithParam.getAttendingCount();
                System.out.println("attending: "+eventAttending);
            } catch (NullPointerException e) {
                System.out.println("attending: "+eventAttending);
            }

            Long eventInterested = null;
            try {
                eventInterested = eventSearchWithParam.getInterestedCount();
                System.out.println("interested: "+eventInterested);
            } catch (NullPointerException e) {
                System.out.println("interested: "+eventInterested);
            }

            Double eventLatitude = null;
            try {
                eventLatitude = eventSearch.getPlace().getLocation().getLatitude();
                System.out.println("latitude: "+eventLatitude);
            } catch (NullPointerException e) {
                System.out.println("latitude: "+eventLatitude);
            }

            Double eventLongitude = null;
            try {
                eventLongitude = eventSearch.getPlace().getLocation().getLongitude();
                System.out.println("longitude: "+eventLongitude);
            } catch (NullPointerException e) {
                System.out.println("longitude: "+eventLongitude);
            }

            String eventUrl = "https://www.facebook.com/events/"+eventId;

            try {
                statement.execute("INSERT INTO APP.EVENTS_ABOUT VALUES ('"+eventId+"', '"+eventName+"', '"+eventCity+"', "
                        + "'"+eventPlace+"', "+eventAttending+", "+eventInterested+", "+eventLatitude+", "+eventLongitude+", "
                        + "'"+eventStartDate+"', '"+eventStartTime+"', '"+eventEndDate+"', "
                        + "'"+eventEndTime+"', '"+eventUrl+"')");
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
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
