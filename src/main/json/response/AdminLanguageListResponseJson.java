package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.LanguageListJson;

public class AdminLanguageListResponseJson extends AbstractResponseJson {

    @Getter
    private LanguageListJson languages;

    public AdminLanguageListResponseJson(LanguageListJson languages, String message, HttpStatus status) {
        super(message, status);
        this.languages = languages;
    }

}
