package main.json.admin.course.edit;

import lombok.EqualsAndHashCode;

import main.json.admin.course.AbstractCourseJson;

import main.json.admin.course.CourseActivityJson;

@EqualsAndHashCode(callSuper = true)
public class EditCourseJson extends AbstractCourseJson {

    public EditCourseJson() {
        super();
    }

    public EditCourseJson(String languageID, String courseTypeID, String courseLevelID, CourseActivityJson courseActivity, int maxStudents, double price) {
        super(languageID, courseTypeID, courseLevelID, courseActivity, maxStudents, price);
    }

}
