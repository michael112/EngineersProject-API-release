package main.json.admin.user.view;

import lombok.EqualsAndHashCode;

import main.json.course.CourseJson;
import main.json.course.CourseUserJson;

@EqualsAndHashCode(callSuper = true)
public class CourseTeacherJson extends CourseJson {

    public void addTeacher(CourseUserJson teacher) {
        super.addTeacher(teacher);
    }

    public CourseTeacherJson(String courseID, String languageID, String languageName, String courseLevel, String courseTypeID, String courseTypeName) {
        super(courseID, languageID, languageName, courseLevel, courseTypeID, courseTypeName);
    }

}
