package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.test.info.TestJson;

public class TestInfoResponseJson extends AbstractResponseJson {

    @Getter
    private TestJson test;

    public TestInfoResponseJson(TestJson test, String message, HttpStatus status) {
        super(message, status);
        this.test = test;
    }

}
