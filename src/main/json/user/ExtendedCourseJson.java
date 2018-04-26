package main.json.user;

import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseUserJson;

import main.json.course.LanguageJson;
import main.json.course.CourseLevelJson;
import main.json.course.CourseTypeJson;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtendedCourseJson extends CourseJson {

    @Getter
    private Set<CourseUserJson> teachers;

    public void addTeacher(CourseUserJson teacher) {
        this.teachers.add(teacher);
    }

    public ExtendedCourseJson(String courseID, LanguageJson language, CourseLevelJson level, CourseTypeJson type) {
        super(courseID, language, level, type);
        this.teachers = new HashSet<>();
    }

    public ExtendedCourseJson(String courseID, LanguageJson language, CourseLevelJson level, CourseTypeJson type, boolean confirmed) {
        super(courseID, language, level, type, confirmed);
        this.teachers = new HashSet<>();
    }

}
