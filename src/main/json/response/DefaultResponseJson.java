package main.json.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultResponseJson extends AbstractResponseJson {

    public DefaultResponseJson(String message, HttpStatus status) {
        super(message, status);
    }

}
