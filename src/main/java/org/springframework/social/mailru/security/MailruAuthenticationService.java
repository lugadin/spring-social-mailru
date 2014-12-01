package org.springframework.social.mailru.security;

import org.springframework.social.mailru.api.Mailru;
import org.springframework.social.mailru.connect.MailruConnectionFactory;
import org.springframework.social.security.provider.OAuth2AuthenticationService;

/**
 * @author Anton Rudenko.
 */
public class MailruAuthenticationService extends OAuth2AuthenticationService<Mailru> {
    public MailruAuthenticationService(String apiKey, String appSecret) {
        super(new MailruConnectionFactory(apiKey, appSecret));
    }

}