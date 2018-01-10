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

    public String getAccessToken() {
        accessToken = "EAATyQwZCvEZB4BAOSoYWaQapIw0QrWD5m1t4kSzqo7C27J8n8ZCCDz7VB3ysDn9WFZAFwrRE63rnmsPiLjyQG16FJ5OLC2fg6PXQM5ZBocTInBtEv4Nx41MsIFXVuQRVCD84wy953Li7nrqefAvUjFuw13TYuXOaIZBy2reJg0ASqoDZB2p7mcgGxLVseS1YJwZD";
        return accessToken;
    }
    
}
