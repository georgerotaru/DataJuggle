/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.datajuggle;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Event;
import com.restfb.types.User;

/**
 *
 * @author admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String accessToken = "EAATyQwZCvEZB4BALFHTlBBNiIpf3mKL7oP650aRXXwwXwQPmc5wMR73fF5l4AO5MN6LWLqyRFRtwZB89OLGGPP98SnZAO952rg2mtqJVFrNBQvhu4kUM11nRah3qFwLkCfg9znuLGggiDoMlcePdMoXNZAS3xJLa8b8WJoIbwB0GXE6pKMhZADS4iy1gRnMboHDHfk3ZATwdgZDZD";
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_11);
                
        //Connection<Event> data1 = fbClient.fetchConnection("search", Event.class,Parameter.with("q","moare"),Parameter.with("type", "event"));
        //System.out.println(data1.getData().get(0).toString());
        
        Event event = fbClient.fetchObject("1924730637778874", Event.class);
        //Connection<Event> connect = fbClient.fetchConnection("1924730637778874", Event.class, Parameter.with("fields", "attending_count,interested_count"));
        //System.out.println(connect.toString());
        
        System.out.println("++");
        System.out.println(event.getAttendingCount());
        
        //http://o7planning.org/en/10191/restfb-java-facebook-graph-api-tutorial
    }
    
}
