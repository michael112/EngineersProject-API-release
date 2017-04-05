package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class CourseMembershipTypeResponseJson extends AbstractResponseJson {

    @Getter
    private String courseMembershipType;

    public CourseMembershipTypeResponseJson(String courseMembershipType, String message, HttpStatus status) {
        super(message, status);
        this.courseMembershipType = courseMembershipType;
    }

}
