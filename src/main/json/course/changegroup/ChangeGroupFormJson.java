package main.json.course.changegroup;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.language.LanguageJson;
import main.json.course.CourseLevelJson;
import main.json.course.CourseTypeJson;

@EqualsAndHashCode
public class ChangeGroupFormJson {

    @Getter
    private LanguageJson language;

    @Getter
    private CourseLevelJson courseLevel;

    @Getter
    private CourseTypeJson courseType;

    @Getter
    private Set<SimilarGroupJson> similarGroups;

    public ChangeGroupFormJson(String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName) {
        this.language = new LanguageJson(languageID, languageName);
        this.courseLevel = new CourseLevelJson(courseLevelID, courseLevelName);
        this.courseType = new CourseTypeJson(courseTypeID, courseTypeName);
        this.similarGroups = new HashSet<>();
    }

    public void addSimilarGroup(SimilarGroupJson similarGroup) {
        this.similarGroups.add(similarGroup);
    }

}
