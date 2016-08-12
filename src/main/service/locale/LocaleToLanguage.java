package main.service.locale;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;

import main.model.language.Language;

public class LocaleToLanguage {

    private HttpServletRequest httpServletRequest;

    private LocaleResolver localeResolver;

    public String getLanguageName(Language language) {
        String localeLanguageCode = this.localeResolver.resolveLocale(this.httpServletRequest).getLanguage();
        return language.getLanguageName(localeLanguageCode);
    }

    public LocaleToLanguage(LocaleResolver localeResolver, HttpServletRequest httpServletRequest) {
        this.localeResolver = localeResolver;
        this.httpServletRequest = httpServletRequest;
    }

}
