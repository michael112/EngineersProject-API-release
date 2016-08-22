package main.json.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponseJson extends AbstractResponseJson {

    public MessageResponseJson(String message, HttpStatus status) {
        super(message, status);
    }

}
