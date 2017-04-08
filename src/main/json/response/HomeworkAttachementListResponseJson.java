package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.homework.info.HomeworkAttachementsJson;

public class HomeworkAttachementListResponseJson extends AbstractResponseJson {

    @Getter
    private HomeworkAttachementsJson attachements;

    public HomeworkAttachementListResponseJson(HomeworkAttachementsJson attachements, String message, HttpStatus status) {
        super(message, status);
        this.attachements = attachements;
    }

}
