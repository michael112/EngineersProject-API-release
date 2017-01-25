package main.json.admin.course.edit;

import lombok.EqualsAndHashCode;

import main.json.admin.course.AbstractCourseJson;

import main.json.admin.course.CourseActivityJson;

@EqualsAndHashCode(callSuper = true)
public class EditCourseJson extends AbstractCourseJson {

    public EditCourseJson() {
        super();
    }

    public EditCourseJson(String languageID, String courseTypeID, String courseLevelName, CourseActivityJson courseActivity, int maxStudents, double price) {
        super(languageID, courseTypeID, courseLevelName, courseActivity, maxStudents, price);
    }

}
