package main.json.admin.language;

import java.util.Set;
import java.util.HashSet;

import javax.validation.Valid;

import lombok.Setter;
import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditLanguageJson {

    @Valid
    @Setter
    @Getter
    public Set<LanguageNameJson> languageNames;

    public void addLanguageName(String languageID, String languageName) {
        this.languageNames.add(new LanguageNameJson(languageID, languageName));
    }

    public EditLanguageJson() {
        super();
        this.languageNames = new HashSet<>();
    }

}
