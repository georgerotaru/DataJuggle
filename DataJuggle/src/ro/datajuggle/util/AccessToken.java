/*
 * Store facebook access token for future use
 */
package ro.datajuggle.util;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;

/**
 * Create FacebookClient object and with FB access token
 * @author George
 */
public class AccessToken {
String accessToken;

    public String AccessToken() {
        accessToken = "EAATyQwZCvEZB4BADZBIY1htuROcYAWpqiZBQEOx07CyQ9Bm6dxX0GLEaY0RAWBBrZAqCA6QzKWZAJmIQS4ZCLu8ZBtKhH0wghEUgjTUqaUmCJUCkM4x7xIalBaok5T4evXULYq2YggOZCpoDsA2LPdbsZB9UMTj35EZCXI4VyxDQdz0zdLuZCiY3P22mjAaILgIfQRAZD";
        return accessToken;
    }
    
}
