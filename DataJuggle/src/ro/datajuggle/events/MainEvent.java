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

/**
 *
 * @author admin
 */
public class MainEvent {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String accessToken = "EAATyQwZCvEZB4BALFHTlBBNiIpf3mKL7oP650aRXXwwXwQPmc5wMR73fF5l4AO5MN6LWLqyRFRtwZB89OLGGPP98SnZAO952rg2mtqJVFrNBQvhu4kUM11nRah3qFwLkCfg9znuLGggiDoMlcePdMoXNZAS3xJLa8b8WJoIbwB0GXE6pKMhZADS4iy1gRnMboHDHfk3ZATwdgZDZD";
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_11);
        
        AttendingEvent att = fbClient.fetchObject("1924730637778874", AttendingEvent.class, Parameter.with("fields", "attending_count,attending"));
        System.out.println(att.getUserId());
    }
    
}
