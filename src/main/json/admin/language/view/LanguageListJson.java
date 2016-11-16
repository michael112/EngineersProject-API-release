package main.json.admin.language.view;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class LanguageListJson {

    @Getter
    private Set<LanguageJson> languages;

    public void addLanguage(LanguageJson language) {
        this.languages.add(language);
    }

    public LanguageListJson() {
        super();
        this.languages = new HashSet<>();
    }

}
