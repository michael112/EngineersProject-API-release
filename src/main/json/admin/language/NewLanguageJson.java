package main.json.admin.language;

import java.util.Set;
import java.util.HashSet;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Setter;
import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NewLanguageJson {

    @NotBlank(message = "language.languageid.empty")
    @Size(min = 2, max = 2, message = "language.languageid.size")
    @Setter
    @Getter
    private String languageID;

    @Valid
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
