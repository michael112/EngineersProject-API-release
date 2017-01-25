package main.json.admin.course;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class NewCourseJson extends AbstractCourseJson {

    public NewCourseJson() {
        super();
    }

    public NewCourseJson(String languageID, String courseTypeID, String courseLevelName, CourseActivityJson courseActivity, int maxStudents, double price) {
        super(languageID, courseTypeID, courseLevelName, courseActivity, maxStudents, price);
    }

}
