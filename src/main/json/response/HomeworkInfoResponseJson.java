package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.homework.info.AbstractHomeworkInfo;

public class HomeworkInfoResponseJson extends AbstractResponseJson {

    @Getter
    AbstractHomeworkInfo homework;

    public HomeworkInfoResponseJson(AbstractHomeworkInfo homework, String message, HttpStatus status) {
        super(message, status);
        this.homework = homework;
    }

}
