package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.language.view.LanguageJson;

public class AdminLanguageInfoResponseJson extends AbstractResponseJson {

    @Getter
    private LanguageJson language;

    public AdminLanguageInfoResponseJson(LanguageJson language, String message, HttpStatus status) {
        super(message, status);
        this.language = language;
    }

}
