package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.test.view.TestListJson;

public class TestListResponseJson extends AbstractResponseJson {

    @Getter
    TestListJson tests;

    public TestListResponseJson(TestListJson tests, String message, HttpStatus status) {
        super(message, status);
        this.tests = tests;
    }

}