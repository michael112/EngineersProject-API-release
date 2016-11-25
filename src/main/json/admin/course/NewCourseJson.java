package main.json.admin.course;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class NewCourseJson extends AbstractCourseJson {

    public NewCourseJson() {
        super();
    }

    public NewCourseJson(String languageID, String courseTypeID, String courseLevelID, CourseActivityJson courseActivity, int maxStudents, double price) {
        super(languageID, courseTypeID, courseLevelID, courseActivity, maxStudents, price);
    }

}
