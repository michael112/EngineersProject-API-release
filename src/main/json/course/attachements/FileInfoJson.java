package main.json.course.attachements;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseUserJson;

@EqualsAndHashCode
public class FileInfoJson {

    @Getter
    private String fileID;

    @Getter
    private String name;

    @Getter
    private String date;

    @Getter
    private String path;

    @Getter
    private CourseUserJson sender;

    public FileInfoJson() {
        super();
    }

    public FileInfoJson(String fileID, String name, String date, String path, String senderID, String senderName) {
        this.fileID = fileID;
        this.name = name;
        this.date = date;
        this.path = path;
        this.sender = new CourseUserJson(senderID, senderName);
    }

}
