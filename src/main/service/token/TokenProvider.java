package main.service.token;

public interface TokenProvider {

    String getToken(String username, String password);

}
