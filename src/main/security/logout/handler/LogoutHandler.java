package main.security.logout.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class LogoutHandler implements LogoutSuccessHandler {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            removeToken(request);
        }
        catch( NullPointerException ex ) {}
    }

    private void removeToken(HttpServletRequest request) throws NullPointerException {
        String tokenValue = getToken(request);
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        tokenStore.removeAccessToken(accessToken);
    }

    private String getToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            String[] tokenTab = authHeader.split(" ");
            String authToken = tokenTab[1];
            return authToken;
        }
        catch( NullPointerException ex) {
            return null;
        }
    }

}
