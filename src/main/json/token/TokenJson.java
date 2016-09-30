package main.json.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
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

    /*
    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            TokenJson other = (TokenJson) otherObj;
            if( !( this.getAccessToken().equals(other.getAccessToken()) ) ) return false;
            if( !( this.getTokenType().equals(other.getTokenType()) ) ) return false;
            if( !( this.getRefreshToken().equals(other.getRefreshToken()) ) ) return false;
            if( this.getExpiresIn() != other.getExpiresIn() ) return false;
            if( !( this.getScope().equals(other.getScope()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
    */
}
