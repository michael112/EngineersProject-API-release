package main.json.admin;

import lombok.Setter;
import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class LanguageNameJson {

    @Setter
    @Getter
    private String namedLanguageID;

    @Setter
    @Getter
    private String namingLanguageID;

    @Setter
    @Getter
    private String languageName;

    public LanguageNameJson() {
        super();
    }

    public LanguageNameJson(String namedLanguageID, String namingLanguageID, String languageName) {
        this();
        this.setNamedLanguageID(namedLanguageID);
        this.setNamingLanguageID(namingLanguageID);
        this.setLanguageName(languageName);
    }

}
