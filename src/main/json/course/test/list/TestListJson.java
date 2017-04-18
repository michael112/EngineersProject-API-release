package main.json.course.test.list;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseJson;

@EqualsAndHashCode(callSuper = true)
public class TestListJson extends CourseJson {

    @Getter
    private Set<TestJson> tests;

    public TestListJson(String courseID, String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName) {
        super(courseID, languageID, languageName, courseLevelID, courseLevelName, courseTypeID, courseTypeName);
        this.tests = new HashSet<>();
    }

    public void addTest(TestJson test) {
        this.tests.add(test);
    }

}
