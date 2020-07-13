package org.danekja.ymanager.ws.rest;

import org.danekja.ymanager.business.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private AuthorizationService authorizationService;

    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final LogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    /**
     * Hackity way of providing connecting apps with means to say where to redirect after login.
     * Example call: GET /api/login/google?target=http:/myfrontapp.com/
     * <p>
     * TODO most likely should be dropped after OAuth2 authentication is triggered and handled by front-end,
     * TODO while backend only accepts the necessary token.
     *
     * @param target
     * @param provider
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("login/{provider}")
    public void login(@RequestParam("target") String target,
                      @PathVariable("provider") String provider,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (authorizationService.isSignedIn()) {
            response.sendRedirect(target);
        } else {
            switch (provider) {
                case "google":
                    this.requestCache.saveRequest(request, response);
                    response.sendRedirect("/api/oauth2/authorization/google");
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }

        }
    }

    @GetMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logoutHandler.logout(request, response, auth);
        }
    }
}
