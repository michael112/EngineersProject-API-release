package main.json.admin.language;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Setter;
import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class LanguageNameJson {

    @NotBlank(message = "languagename.languageid.empty")
    @Size(min = 2, max = 2, message = "languagename.languageid.size")
    @Setter
    @Getter
    private String languageID;

    @NotBlank(message = "languagename.name.empty")
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
