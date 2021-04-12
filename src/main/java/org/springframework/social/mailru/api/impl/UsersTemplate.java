package org.springframework.social.mailru.api.impl;

import org.springframework.social.mailru.api.MailruProfile;
import org.springframework.social.mailru.api.UsersOperations;
import org.springframework.social.support.URIBuilder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User operations.
 *
 * @author Cackle
 */
public class UsersTemplate extends AbstractMailruOperations implements UsersOperations {

    private static final String METHOD = "users.getInfo";

    private final RestTemplate restTemplate;

    public UsersTemplate(String clientId, String clientSecret, RestTemplate restTemplate,
                         String accessToken, boolean isAuthorizedForUser) {



        super(clientId, clientSecret, accessToken, isAuthorizedForUser);
        this.restTemplate = restTemplate;
    }

    @Override
    public MailruProfile getProfile() {


        requireAuthorization();

        Map<String, String> params = new HashMap<String, String>();
        params.put("method", METHOD);
        URI uri = URIBuilder.fromUri(makeOperationURL2(params)).build();

        Map<String, Object> profiles = restTemplate.getForObject(uri, Map.class);
        //checkForError(profiles);

        if (!profiles.isEmpty()) {

            MailruProfile profile = new MailruProfile(
                    (String)profiles.get("id"),
                    (String)profiles.get("first_name"),
                    (String)profiles.get("last_name"),
                    (String)profiles.get("email"),
                    (String)profiles.get("link")
            );

            if (profiles.containsKey("image")) {
                profile.setPhoto((String)profiles.get("image"));
            }

            if (profiles.containsKey("birthday")) {
                profile.setBirthday((String)profiles.get("birthday"));
            }

            return profile;
        }
        return null;
    }

    private class Ans {
    }
}
