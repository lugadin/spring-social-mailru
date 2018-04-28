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
        URI uri = URIBuilder.fromUri(makeOperationURL(params)).build();

        List<Map<String, Object>> profiles = restTemplate.getForObject(uri, List.class);
        //checkForError(profiles);

        if (!profiles.isEmpty()) {
            Map<String, Object> profilesMap = profiles.get(0);
            MailruProfile profile = new MailruProfile(
                    (String)profilesMap.get("uid"),
                    (String)profilesMap.get("first_name"),
                    (String)profilesMap.get("last_name"),
                    (String)profilesMap.get("email"),
                    (String)profilesMap.get("link"),
                    (Integer)profilesMap.get("sex")
            );

            if (profilesMap.containsKey("pic")) {
                profile.setPhoto((String)profilesMap.get("pic"));
            }

            if (profilesMap.containsKey("birthday")) {
                profile.setBirthday((String)profilesMap.get("birthday"));
            }

            return profile;
        }
        return null;
    }
}
