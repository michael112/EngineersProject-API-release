package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public abstract class AbstractResponseJson {

    @Getter
    private boolean success;
    @Getter
    protected String message;

    protected void setSuccess(HttpStatus status) {
        this.success = (status == HttpStatus.OK);
    }

    protected AbstractResponseJson(String message, HttpStatus status) {
        this.message = message;
        this.setSuccess(status);
    }

}
