package main.json.response;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class ValidationErrorResponseJson extends AbstractResponseJson {

    @Getter
    private List<String> messages;

    public ValidationErrorResponseJson(List<String> messages, HttpStatus status) {
        super(null, status);
        if( messages != null ) {
            this.messages = messages;
        }
        else {
            this.messages = new ArrayList<>();
        }
    }

}
