package main.json.admin.language;

import java.util.Set;
import java.util.HashSet;

import lombok.Setter;
import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NewLanguageJson {

    @Setter
    @Getter
    private String languageID;

    @Setter
    @Getter
    public Set<LanguageNameJson> languageNames;

    public void addLanguageName(String languageID, String languageName) {
        this.languageNames.add(new LanguageNameJson(languageID, languageName));
    }

    public NewLanguageJson() {
        super();
        this.languageNames = new HashSet<>();
    }

    public NewLanguageJson(String languageID) {
        this();
        this.setLanguageID(languageID);
    }

}
