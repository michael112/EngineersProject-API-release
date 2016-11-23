package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.language.view.LanguageListJson;
import main.json.admin.level.CourseLevelListJson;
import main.json.admin.type.view.CourseTypeListJson;

public class AdminCreatingCourseDataResponseJson extends AbstractResponseJson {

    @Getter
    private LanguageListJson languages;

    @Getter
    private CourseLevelListJson courseLevels;

    @Getter
    private CourseTypeListJson courseTypes;

    public AdminCreatingCourseDataResponseJson(LanguageListJson languages, CourseLevelListJson courseLevels, CourseTypeListJson courseTypes, String message, HttpStatus status) {
        super(message, status);
        this.languages = languages;
        this.courseLevels = courseLevels;
        this.courseTypes = courseTypes;
    }

}