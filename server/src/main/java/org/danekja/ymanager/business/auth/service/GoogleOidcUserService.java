package org.danekja.ymanager.business.auth.service;

import org.danekja.ymanager.business.UserManager;
import org.danekja.ymanager.domain.GoogleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Customer {@link OAuth2UserService} of OpenID protocol, which populates user
 * object from data stored in database.
 * <p>
 * If the authenticated user does not have record in database, it is created.
 */
@Transactional
@Service
public class GoogleOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final UserManager userManager;

    @Value("#{'${ymanager.oauth2.client.google.allowed-domains}'.split(',')}")
    private List<String> allowedDomains;

    @Autowired
    public GoogleOidcUserService(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        //this ensures only users from configured domains can log-in
        //also, Google allows to configure application as "internal", after that only accounts from your domain
        //can sign-in
        if (!allowedDomains.isEmpty() && !allowedDomains.contains(userRequest.getIdToken().getClaimAsString("hd"))) {
            throw new OAuth2AuthenticationException(new OAuth2Error("403"), "Unauthorized!");
        }

        OidcIdToken token = userRequest.getIdToken();

        GoogleUser user = userManager.getUser(userRequest.getIdToken());

        if (user == null) {
            user = userManager.registerUser(token);
        }

        return user;
    }
}
