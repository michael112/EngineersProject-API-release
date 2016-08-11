package main.json.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponseJson extends ResponseJson {

    public MessageResponseJson(String message, HttpStatus status) {
        this.message = message;
        this.setSuccess(status);
    }

}
