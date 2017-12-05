/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.datajuggle;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.Group;

/**
 *
 * @author admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String accessToken = "EAATyQwZCvEZB4BADGVRx7mjOFEuQp8SAa0UZAD4iDx5O2bDImZAZCRZBJ5mYCcdlOw2kzLQL8I1FheZBgojcXyGTZCtTni4u0gPwrGQcSLQ7yH1f23RtKh2Y4ieZAji79h7OMNZB1ZCA55hpZBuTRzGaQpuk5gjE8UkPidqQipyWgUNqXsAtHgcorlKAOUCMntymECgZD";
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_11);
                
        /*User me = fbClient.fetchObject("100000546169949", User.class);
        System.out.println(me.toString());*/
        
        Group aGroup = fbClient.fetchObject("179586658890209", Group.class);
        System.out.println(aGroup.getParent());
        
        Connection<Group> result = fbClient.fetchConnection("179586658890209", Group.class);
        System.out.println(result.toString());
        /*Connection<Group> result = fbClient.fetchConnection("179586658890209", Group.class);
        int counter = 0;
        for (List<Group> page:result) {
            for (Group aPost:page) {
                System.out.println(aPost.);
                counter++;
            }
        }
        System.out.println("Number of results: "+counter);*/

        
        
    }
    
}
