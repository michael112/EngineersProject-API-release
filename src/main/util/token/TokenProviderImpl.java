package main.util.token;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.text.StrSubstitutor;

import main.util.domain.DomainURIProvider;

import main.util.properties.PropertyProvider;

import main.json.token.TokenJson;

@Service("tokenProvider")
public class TokenProviderImpl implements TokenProvider {

    @Autowired
    private DomainURIProvider domainURIProvider;

    @Autowired
    private PropertyProvider propertyProvider;

    private ObjectMapper objectMapper;

    @PostConstruct
    public void initialize() {
        this.objectMapper = new ObjectMapper();
    }

    public TokenJson getToken(String username, String password) {
        String uri = getTokenURI(username, password);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> tokenResultStr = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        try {
            TokenJson tokenResult = this.objectMapper.readValue(tokenResultStr.getBody(), TokenJson.class);
            return tokenResult;
        }
        catch( IOException ex ) {
            return null;
        }
    }

    public String getAccessToken(String username, String password) {
        try {
            TokenJson tokenResult = this.getToken(username, password);
            return tokenResult.getAccessToken();
        }
        catch( NullPointerException ex ) {
            return null;
        }
    }

    private String getTokenURI(String username, String password) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("getTokenURL", this.propertyProvider.getProperty("oauth.gettoken.url"));
        valuesMap.put("client", this.propertyProvider.getProperty("oauth.token.client"));
        valuesMap.put("username", username);
        valuesMap.put("password", password);
        StrSubstitutor strSubstitutor = new StrSubstitutor(valuesMap);
        String templateString = this.propertyProvider.getProperty("oauth.login.fullurl");

        return this.domainURIProvider.getDomainURI() + strSubstitutor.replace(templateString);
    }

    public void deactivateToken(String authorizationHeader) {
        String uri = this.domainURIProvider.getDomainURI() + this.propertyProvider.getProperty("oauth.logout.url");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", authorizationHeader);
        HttpEntity<String> httpEntity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> tokenResultStr = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
    }

}
