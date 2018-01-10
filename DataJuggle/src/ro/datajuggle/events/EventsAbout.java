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
        //SetDate setDate = new SetDate();
        //setDate.inputDate();
        //long unixDate = setDate.transformToUnixTimestamp();
        Event eventSearch = fbClient.fetchObject(id, Event.class);
        Event eventSearch1 = fbClient.fetchObject(id, Event.class, Parameter.with("fields", "attending_count,interested_count"));
        //Connection<Event> eventConnection = fbClient.fetchConnection("search", Event.class, Parameter.with(id, args));
        
        String eventId = eventSearch.getId();
        System.out.println("id: "+eventId);
        
        String eventName = eventSearch.getName();
        System.out.println("name: "+eventName);
        
        String eventCity = eventSearch.getPlace().getLocation().getCity();
        System.out.println("city: "+eventCity);
        
        String eventPlace = eventSearch.getPlace().getName();
        System.out.println("place: "+eventPlace);
        
        DateFormat romanianDate = new SimpleDateFormat("dd.MM.yyyy"); 
        DateFormat shortTime = new SimpleDateFormat("HH:mm:ss");
        
        Date eventStart = eventSearch.getStartTime();
        System.out.println("start_date: "+romanianDate.format(eventStart));
        System.out.println("start_time: "+shortTime.format(eventStart));
        
        Date eventEnd = eventSearch.getEndTime();
        System.out.println("end_date: "+romanianDate.format(eventEnd));
        System.out.println("end_time: "+shortTime.format(eventEnd));
        
        Integer eventAttending = eventSearch1.getAttendingCount();
        System.out.println("attending: "+eventAttending);
        
        Long eventInterested = eventSearch1.getInterestedCount();
        System.out.println("interested: "+eventInterested);
        
        Double eventLatitude = eventSearch.getPlace().getLocation().getLatitude();
        System.out.println("latitude: "+eventLatitude);
        
        Double eventLongitude = eventSearch.getPlace().getLocation().getLongitude();
        System.out.println("longitude: "+eventLongitude);
        
        db.statement.execute("INSERT INTO APP.EVENTS_ABOUT VALUES ('"+eventId+"', '"+eventName+"', '"+eventCity+"',"
                + "'"+eventPlace+"',"+eventAttending+", "+eventInterested+", "+eventLatitude+", "+eventLongitude+", "
                + "'"+romanianDate.format(eventStart)+"', '"+shortTime.format(eventStart)+"', '"+romanianDate.format(eventEnd)+"',"
                + "'"+shortTime.format(eventEnd)+"')");
    }
    
}
