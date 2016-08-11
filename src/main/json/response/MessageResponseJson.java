package main.json.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponseJson extends ResponseJson {

    @Getter
    private boolean success;

    @Getter
    private String message;

    public MessageResponseJson(String message, HttpStatus status) {
        this.message = message;
        this.success = (status == HttpStatus.OK);
    }

}
