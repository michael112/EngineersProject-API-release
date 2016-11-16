package main.json.admin.language;

import lombok.Setter;
import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class LanguageNameJson {

    @Setter
    @Getter
    private String languageID;

    @Getter
    @Setter
    private String languageName;

    public LanguageNameJson() {
        super();
    }

    public LanguageNameJson(String languageID, String languageName) {
        this();
        this.setLanguageID(languageID);
        this.setLanguageName(languageName);
    }

}
