package org.springframework.social.mailru.api.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.mailru.api.WallOperations;
import org.springframework.social.support.URIBuilder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WallTemplate extends AbstractMailruOperations implements WallOperations {

    private static final String METHOD = "stream.post";

    private final RestTemplate restTemplate;

    public WallTemplate(String clientId, String clientSecret, RestTemplate restTemplate,
                        String accessToken, boolean isAuthorizedForUser) {

        super(clientId, clientSecret, accessToken, isAuthorizedForUser);
        this.restTemplate = restTemplate;
    }

    @Override
    public String post(String message) {
        requireAuthorization();

        final String msg = StringUtils.length(message) > 400 ? StringUtils.substring(message, 0, 400) : message;

        Map<String, String> params = new HashMap<String, String>();
        params.put("method", METHOD);
        params.put("text", msg);
        URI uri = URIBuilder.fromUri(makeOperationURL(params)).build();

        List result = restTemplate.getForObject(uri, List.class);
        return StringUtils.EMPTY;
    }

}
