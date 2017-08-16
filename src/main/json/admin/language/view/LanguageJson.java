package main.json.admin.language.view;

import java.util.Set;
import java.util.HashSet;

import lombok.Setter;
import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class LanguageJson {

    @Setter
    @Getter
    private String languageID;

    @Getter
    @Setter
    private String languageName;

    @Setter
    @Getter
    public Set<LanguageNameJson> languageNames;

    @Setter
    @Getter
    private boolean hasCourses;

    @Setter
    @Getter
    private boolean hasPlacementTests;

    public void addLanguageName(String namedLanguageID, String namingLanguageID, String languageName) {
        this.languageNames.add(new LanguageNameJson(namedLanguageID, namingLanguageID, languageName));
    }

    public LanguageJson() {
        super();
        this.languageNames = new HashSet<>();
    }

    public LanguageJson(String languageID, String languageName, boolean hasCourses, boolean hasPlacementTests) {
        this();
        this.setLanguageID(languageID);
        this.setLanguageName(languageName);
        this.setHasCourses(hasCourses);
        this.setHasPlacementTests(hasPlacementTests);
    }

}
