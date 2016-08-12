package main.service.localetolanguage;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;

import main.model.language.Language;

public class LocaleToLanguageService {

    private HttpServletRequest httpServletRequest;

    private LocaleResolver localeResolver;

    public String getLanguageName(Language language) {
        String localeLanguageCode = this.localeResolver.resolveLocale(this.httpServletRequest).getLanguage();
        return language.getLanguageName(localeLanguageCode);
    }

    public LocaleToLanguageService(LocaleResolver localeResolver, HttpServletRequest httpServletRequest) {
        this.localeResolver = localeResolver;
        this.httpServletRequest = httpServletRequest;
    }

}
