package main.json.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class TokenJson {

    @Getter
    @Setter
    @JsonProperty("access_token")
    private String accessToken;

    @Getter
    @Setter
    @JsonProperty("token_type")
    private String tokenType;

    @Getter
    @Setter
    @JsonProperty("refresh_token")
    private String refreshToken;

    @Getter
    @Setter
    @JsonProperty("expires_in")
    private int expiresIn;

    @Getter
    @Setter
    private String scope;

}
