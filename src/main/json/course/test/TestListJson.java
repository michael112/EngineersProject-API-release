package main.json.course.test;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import main.json.course.CourseJson;

public class TestListJson extends CourseJson {

    @Getter
    private Set<TestJson> tests;

    public TestListJson(String courseID, String languageID, String languageName, String courseLevel, String courseTypeID, String courseTypeName) {
        super(courseID, languageID, languageName, courseLevel, courseTypeID, courseTypeName);
        this.tests = new HashSet<>();
    }

    public void addTest(TestJson test) {
        this.tests.add(test);
    }

}
