package main.json.course.changegroup;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.language.LanguageJson;
import main.json.course.CourseLevelJson;
import main.json.course.CourseTypeJson;
import main.json.course.CourseUserJson;

@EqualsAndHashCode
public class ChangeGroupFormJson {

    @Getter
    private LanguageJson language;

    @Getter
    private CourseLevelJson courseLevel;

    @Getter
    private CourseTypeJson courseType;

    @Getter
    private Set<CourseUserJson> teachers;

    @Getter
    private Set<SimilarGroupJson> similarGroups;

    @Getter
    private Double price;

    public ChangeGroupFormJson(String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName, Double price) {
        this.language = new LanguageJson(languageID, languageName);
        this.courseLevel = new CourseLevelJson(courseLevelID, courseLevelName);
        this.courseType = new CourseTypeJson(courseTypeID, courseTypeName);
        this.teachers = new HashSet<>();
        this.similarGroups = new HashSet<>();
        this.price = price;
    }

    public void addTeacher(CourseUserJson teacher) {
        this.teachers.add(teacher);
    }

    public void addSimilarGroup(SimilarGroupJson similarGroup) {
        this.similarGroups.add(similarGroup);
    }

}
