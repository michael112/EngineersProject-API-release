package main.json.course.language;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class LanguageJson {

	@Getter
	private String id;

	@Getter
	private String name;

    public LanguageJson(String languageID, String languageName) {
        this.id = languageID;
        this.name = languageName;
    }

}