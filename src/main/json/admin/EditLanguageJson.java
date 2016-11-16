package main.json.admin;

import java.util.Set;
import java.util.HashSet;

import lombok.Setter;
import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditLanguageJson {

    @Setter
    @Getter
    public Set<LanguageNameJson> languageNames;

    public void addLanguageName(String namedLanguageID, String namingLanguageID, String languageName) {
        this.languageNames.add(new LanguageNameJson(namedLanguageID, namingLanguageID, languageName));
    }

    public EditLanguageJson() {
        super();
        this.languageNames = new HashSet<>();
    }

}
