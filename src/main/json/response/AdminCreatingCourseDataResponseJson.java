package main.json.response;

import java.util.Set;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.language.view.LanguageListJson;
import main.json.admin.level.view.CourseLevelListJson;
import main.json.admin.type.view.CourseTypeListJson;

import main.json.course.CourseDaySetJson;

public class AdminCreatingCourseDataResponseJson extends AbstractResponseJson {

    @Getter
    private LanguageListJson languages;

    @Getter
    private CourseLevelListJson courseLevels;

    @Getter
    private CourseTypeListJson courseTypes;

    @Getter
    private Set<CourseDaySetJson> courseDays;

    public AdminCreatingCourseDataResponseJson(LanguageListJson languages, CourseLevelListJson courseLevels, CourseTypeListJson courseTypes, Set<CourseDaySetJson> days, String message, HttpStatus status) {
        super(message, status);
        this.languages = languages;
        this.courseLevels = courseLevels;
        this.courseTypes = courseTypes;
        this.courseDays = days;
    }

}