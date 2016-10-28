package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.course.attachements.FileInfoListJson;

public class FileInfoResponseJson extends AbstractResponseJson {

    @Getter
    private FileInfoListJson attachements;

    public FileInfoResponseJson(FileInfoListJson attachements, String message, HttpStatus status) {
        super(message, status);
        this.attachements = attachements;
    }

}
