/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.datajuggle.events;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.Event;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author admin
 */
public class MainCustomParameterEvent {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String accessToken = "EAACEdEose0cBAExOZAQ0mziZCbSxcHVm7pBsv69YK9rppZAbj2MzgWEZBxqZC4j50fz52lGx7rKfl3IOHZBpaPqbSZAhZA6AFmS37DLWmFnJr7jLFSlLceI9TiFnbr4JyojFTXDQznqYzIOA38p6Bu6qP6ZC29ytmE76pB0VcQTG2bQLxo4ZBHQVL8lA6ZCnFbderKlveP6h57hSgZDZD";
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_11);
        String id;
        String eventParameter;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input search parameter: ");
        eventParameter = scanner.nextLine();
        Connection<JsonObject> targetedSearch = fbClient.fetchConnection("search", JsonObject.class, Parameter.with("q", eventParameter), Parameter.with("type", "event"));

        int counter = 0;
        for (List<JsonObject> page : targetedSearch) {
            int insiderCounter = 0;
            for (JsonObject oneByOne : page) {
                id = targetedSearch.getData().get(insiderCounter).getString("id","I cannot find any Event ID");
                Event event = fbClient.fetchObject(id, Event.class, Parameter.with("fields", "interested_count,attending_count,description,end_time,id,name,place,start_time,admins"));
                System.out.println("\033[0;31m"+"Link to event: "+"\033[0m"+"https://www.facebook.com/"+event.getId());
                System.out.println("\033[0;31m"+"Name of event: "+"\033[0m"+event.getName());
                System.out.println("\033[0;31m"+"Event description: "+"\033[0m"+event.getDescription());
                System.out.println("\033[0;31m"+"From "+"\033[0m"+event.getStartTime()+"\033[0;31m"+" to "+"\033[0m"+event.getEndTime());
                System.out.println("\033[0;31m"+"People going: "+"\033[0m"+event.getAttendingCount());
                System.out.println("\033[0;31m"+"People interested: "+"\033[0m"+event.getInterestedCount());

                try { System.out.println("\033[0;31m"+"Country: "+"\033[0m"+event.getPlace().getLocation().getCountry());
                } catch (NullPointerException e) {
                    System.out.println("Country: no country set");
                }
                 try { System.out.println("\033[0;31m"+"City: "+"\033[0m"+event.getPlace().getLocation().getCity());
                } catch (NullPointerException e) {
                    System.out.println("\033[0;31mCity: \033[0mno city set");
                }
                try { System.out.println("\033[0;31m"+"Place: "+"\033[0m"+event.getPlace().getName());
                } catch (NullPointerException e) {
                    System.out.println("\033[0;31mPlace: \033[0mno place set");
                }
                try { System.out.println("\033[0;31m"+"Admin: "+"\033[0m"+event.getOwner().getCategoryList().toString());
                } catch (NullPointerException e) {
                    System.out.println("\033[0;31mAdmin: \033[0mno admin found");
                }
                insiderCounter++;
                counter++;
                System.out.println("*-*-*-*--**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
            }
        }
        System.out.println("Total events for your search: "+counter+" (using the parameter "+eventParameter+")");
        
    }
    
}
