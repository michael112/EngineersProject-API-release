package main.json.course.attachements;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

import main.json.course.CourseUserJson;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @Getter
    private boolean isRemote;

    @Getter
    private String remoteID;

    public FileInfoJson() {
        super();
    }

    public FileInfoJson(String fileID, String name, String date, String path, String senderID, String senderName, boolean isRemote) {
        this.fileID = fileID;
        this.name = name;
        this.date = date;
        this.path = path;
        this.sender = new CourseUserJson(senderID, senderName);
        this.isRemote = isRemote;
    }

    public FileInfoJson(String fileID, String name, String date, String senderID, String senderName, boolean isRemote, String remoteID) {
        this.fileID = fileID;
        this.name = name;
        this.date = date;
        this.sender = new CourseUserJson(senderID, senderName);
        this.isRemote = isRemote;
        this.remoteID = remoteID;
    }

}
