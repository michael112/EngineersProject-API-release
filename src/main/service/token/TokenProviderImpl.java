package main.service.token;

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

import main.service.domain.DomainURIProvider;

import main.service.properties.PropertyUtil;

import main.json.token.TokenJson;

@Service("tokenProvider")
public class TokenProviderImpl implements TokenProvider {

    @Autowired
    private DomainURIProvider domainURIProvider;

    @Autowired
    private PropertyUtil propertyUtil;

    private ObjectMapper objectMapper;

    @PostConstruct
    public void initialize() {
        this.objectMapper = new ObjectMapper();
    }

    public String getToken(String username, String password) {
        String uri = getTokenURI(username, password);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> tokenResultStr = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        try {
            TokenJson tokenResult = this.objectMapper.readValue(tokenResultStr.getBody(), TokenJson.class);
            return tokenResult.getAccessToken();
        }
        catch( IOException ex ) {
            return null;
        }
    }

    private String getTokenURI(String username, String password) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("getTokenURL", this.propertyUtil.getProperty("oauth.gettoken.url"));
        valuesMap.put("client", this.propertyUtil.getProperty("oauth.token.client"));
        valuesMap.put("username", username);
        valuesMap.put("password", password);
        StrSubstitutor strSubstitutor = new StrSubstitutor(valuesMap);
        String templateString = this.propertyUtil.getProperty("oauth.login.fullurl");

        return this.domainURIProvider.getDomainURI() + strSubstitutor.replace(templateString);
    }

}
