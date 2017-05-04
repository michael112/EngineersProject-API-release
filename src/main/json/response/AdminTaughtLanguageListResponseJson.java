package main.json.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import main.json.admin.language.teacher.TaughtLanguageListJson;

public class AdminTaughtLanguageListResponseJson extends AbstractResponseJson {

    @Getter
    private TaughtLanguageListJson taughtLanguages;

    public AdminTaughtLanguageListResponseJson(TaughtLanguageListJson taughtLanguages, String message, HttpStatus status) {
        super(message, status);
        this.taughtLanguages = taughtLanguages;
    }

}
