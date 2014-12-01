/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.mailru.connect;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Mailru-specific extension of OAuth2Template.
 * @author Cackle
 */
public class MailruOAuth2Template extends OAuth2Template {

    private String uid;

	public MailruOAuth2Template(String clientId, String clientSecret) {
		super(clientId, clientSecret, "https://connect.mail.ru/oauth/authorize", "https://connect.mail.ru/oauth/token");
	    setUseParametersForClientAuthentication(true);
    }

    @Override
    protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, Map<String, Object> response) {
        uid = (String) response.get("x_mailru_vid");
        return super.createAccessGrant(accessToken, scope, refreshToken, expiresIn == 0 ? null : expiresIn, response);
    }

    public String getUid() {
        return uid;
    }

    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        Map<String, Object> responseMap = getRestTemplate().postForObject(accessTokenUrl, parameters, Map.class);
        return this.extractAccessGrant(responseMap);
    }

    private AccessGrant extractAccessGrant(Map<String, Object> result) {
        String accessToken = (String)result.get("access_token");
        String scope = (String)result.get("scope");
        String refreshToken = (String)result.get("refresh_token");
        Number expiresInNumber = (Number) result.get("expires_in");
        Long expiresIn = expiresInNumber == null?null:Long.valueOf(expiresInNumber.longValue());
        return this.createAccessGrant(accessToken, scope, refreshToken, expiresIn, result);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();

        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;

                List<MediaType> mTypes = new LinkedList<MediaType>(jsonConverter.getSupportedMediaTypes());
                mTypes.add(new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
                jsonConverter.setSupportedMediaTypes(mTypes);
            }
        }

        return restTemplate;
    }
}
