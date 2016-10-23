package main.json.course;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class CourseSignupJson extends CourseJson {

    @Getter
    private Double price;

    public CourseSignupJson(String courseID, String languageID, String languageName, String courseLevel, String courseTypeID, String courseTypeName, Double price) {
        super(courseID, languageID, languageName, courseLevel, courseTypeID, courseTypeName);
        this.price = price;
    }

    public void addTeacher(CourseUserJson teacher) {
        super.addTeacher(teacher);
    }

}
