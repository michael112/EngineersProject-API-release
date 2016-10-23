package main.json.course.changegroup;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.language.LanguageJson;

@EqualsAndHashCode
public class ChangeGroupFormJson {

    @Getter
    private LanguageJson language;

    @Getter
    private String courseLevel;

    @Getter
    private String courseType;

    @Getter
    private Set<SimilarGroupJson> similarGroups;

    public ChangeGroupFormJson(String languageID, String languageName, String courseLevel, String courseType) {
        this.language = new LanguageJson(languageID, languageName);
        this.courseLevel = courseLevel;
        this.courseType = courseType;
        this.similarGroups = new HashSet<>();
    }

    public void addSimilarGroup(SimilarGroupJson similarGroup) {
        this.similarGroups.add(similarGroup);
    }

}
