package main.json.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.LanguageJson;
import main.json.course.CourseLevelJson;
import main.json.course.CourseTypeJson;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseJson {

    @Getter
    private String courseID;
    @Getter
    private LanguageJson language;
    @Getter
    private CourseLevelJson courseLevel;
    @Getter
    private CourseTypeJson courseType;
    @Getter
    private Boolean confirmed;

    public CourseJson(String courseID, LanguageJson language, CourseLevelJson level, CourseTypeJson type) {
        super();
        this.courseID = courseID;
        this.language = language;
        this.courseLevel = level;
        this.courseType = type;
        this.confirmed = null;
    }
    public CourseJson(String courseID, LanguageJson language, CourseLevelJson level, CourseTypeJson type, boolean confirmed) {
        this(courseID, language, level, type);
        this.confirmed = confirmed;
    }

}
