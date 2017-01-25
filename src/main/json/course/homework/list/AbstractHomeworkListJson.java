package main.json.course.homework.list;

import lombok.EqualsAndHashCode;

import main.json.course.CourseJson;

@EqualsAndHashCode(callSuper = true)
public class AbstractHomeworkListJson extends CourseJson {

    protected AbstractHomeworkListJson(String courseID, String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName) {
        super(courseID, languageID, languageName, courseLevelID, courseLevelName, courseTypeID, courseTypeName);
    }

}
