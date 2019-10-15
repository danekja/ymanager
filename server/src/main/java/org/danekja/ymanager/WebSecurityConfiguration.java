package org.danekja.ymanager;

import org.danekja.ymanager.business.UserManager;
import org.danekja.ymanager.business.auth.service.GoogleOidcUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2UserService<OidcUserRequest, OidcUser> googleOauthUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .userInfoEndpoint().oidcUserService(googleOauthUserService);
    }

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> googleOauthUserService(UserManager userManager) {
        return new GoogleOidcUserService(userManager);
    }

    /**
     * This is a dummy authentication provider which does not check password at all.
     * <p>
     * To be replaced with OAuth2 client
     *
     * @param userDetailsService
     * @return
     */
    @Bean
    public AuthenticationProvider dummyAuthManager(UserDetailsService userDetailsService) {
        return new AbstractUserDetailsAuthenticationProvider() {

            private final Logger LOG = LoggerFactory.getLogger("DummyLogger");

            @Override
            protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

            }

            @Override
            protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
                return userDetailsService.loadUserByUsername(username);
            }
        };
    }

}
