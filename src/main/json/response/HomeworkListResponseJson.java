package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.homework.list.AbstractHomeworkListJson;

public class HomeworkListResponseJson extends AbstractResponseJson {

    @Getter
    AbstractHomeworkListJson homeworks;

    public HomeworkListResponseJson(AbstractHomeworkListJson homeworks, String message, HttpStatus status) {
        super(message, status);
        this.homeworks = homeworks;
    }

}
