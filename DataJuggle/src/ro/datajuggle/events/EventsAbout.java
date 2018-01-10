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
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import ro.datajuggle.util.AccessToken;
import ro.datajuggle.util.DateToCalendar;

/**
 *
 * @author admin
 */
public class EventsAbout {

    public EventsAbout() throws SQLException, ClassNotFoundException, NullPointerException {
        DBConnect db = new DBConnect();
        DateToCalendar dtc = new DateToCalendar();
        FacebookClient fbClient = new DefaultFacebookClient(new AccessToken().getAccessToken(), Version.VERSION_2_11);
        String id;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input event id: ");
        id = scanner.nextLine();

        Event eventSearch = fbClient.fetchObject(id, Event.class);
        Event eventSearchWithParam = fbClient.fetchObject(id, Event.class, Parameter.with("fields", "attending_count,interested_count"));
        
        String eventId = null;
        try {
            eventId = eventSearch.getId();
            System.out.println("id: "+eventId);
        } catch (NullPointerException e) {
            System.out.println("id: "+eventId);
        }
               
        String eventName = null;
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
            eventPlace = eventSearch.getPlace().getName();
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
        
        String eventUrl = "https://www.facebook.com/events/"+id;
        
        db.statement.execute("INSERT INTO APP.EVENTS_ABOUT VALUES ('"+eventId+"', '"+eventName+"', '"+eventCity+"', "
                + "'"+eventPlace+"', "+eventAttending+", "+eventInterested+", "+eventLatitude+", "+eventLongitude+", "
                + "'"+eventStartDate+"', '"+eventStartTime+"', '"+eventEndDate+"', "
                + "'"+eventEndTime+"', '"+eventUrl+"')");
    }
    
}
