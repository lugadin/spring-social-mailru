package org.springframework.social.mailru.config.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.xml.ApiHelper;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.mailru.api.Mailru;
import org.springframework.social.mailru.api.impl.MailruTemplate;

public class MailruApiHelper implements ApiHelper<Mailru> {

    private final static Log logger = LogFactory.getLog(MailruApiHelper.class);
    private final UsersConnectionRepository usersConnectionRepository;
    private final UserIdSource userIdSource;

    private MailruApiHelper(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
        this.usersConnectionRepository = usersConnectionRepository;
        this.userIdSource = userIdSource;
    }

    public Mailru getApi() {
        if (logger.isDebugEnabled()) {
            logger.debug("Getting API binding instance for Facebook");
        }

        Connection<Mailru> connection = usersConnectionRepository.createConnectionRepository(userIdSource.getUserId()).findPrimaryConnection(Mailru.class);
        if (logger.isDebugEnabled() && connection == null) {
            logger.debug("No current connection; Returning default FacebookTemplate instance.");
        }
        return connection != null ? connection.getApi() : new MailruTemplate();
    }

}
