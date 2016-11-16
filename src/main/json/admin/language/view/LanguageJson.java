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

    @Setter
    @Getter
    public Set<LanguageNameJson> languageNames;

    public void addLanguageName(String namedLanguageID, String namingLanguageID, String languageName) {
        this.languageNames.add(new LanguageNameJson(namedLanguageID, namingLanguageID, languageName));
    }

    public LanguageJson() {
        super();
        this.languageNames = new HashSet<>();
    }

    public LanguageJson(String languageID) {
        this();
        this.setLanguageID(languageID);
    }

}
