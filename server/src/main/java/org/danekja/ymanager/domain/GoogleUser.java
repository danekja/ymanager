package org.danekja.ymanager.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Represents user authenticated via Google OAuth2 provider
 */
public class GoogleUser extends User implements OidcUser {

    private static final String HOSTED_DOMAIN = "hd";

    private final OidcIdToken idToken;

    public GoogleUser(Long id, UserData userData, OidcIdToken idToken) {
        super(id, userData);
        this.idToken = idToken;
    }

    public GoogleUser(OidcIdToken idToken) {
        this.idToken = idToken;
    }

    public String subjectId() {
        return idToken.getSubject();
    }

    @Override
    public String getFirstName() {
        return idToken.getGivenName();
    }

    @Override
    public String getLastName() {
        return idToken.getFamilyName();
    }

    @Override
    public String getEmail() {
        return idToken.getEmail();
    }

    @Override
    public String getPicture() {
        return idToken.getPicture();
    }

    public String getDomain() {
        return idToken.getClaimAsString(HOSTED_DOMAIN);
    }

    @Override
    public Map<String, Object> getClaims() {
        return idToken.getClaims();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return idToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //TODO do not create collection on each call
        return Collections.singleton(new SimpleGrantedAuthority(userData.getRole().name()));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return idToken.getClaims();
    }

    @Override
    public String getName() {
        return idToken.getFullName();
    }
}
