package main.util.token;

import main.json.token.TokenJson;

public interface TokenProvider {

    String getAccessToken(String username, String password);

    TokenJson getToken(String username, String password);

    void deactivateToken(String authorizationHeader);

}
