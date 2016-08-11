package main.service.localetolanguage;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.beans.factory.annotation.Autowired;

import main.model.language.Language;

public class LocaleToLanguageService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    private LocaleResolver localeResolver;
    public void setLocaleResolver(LocaleResolver localeResolver) { this.localeResolver = localeResolver; }

    public String getLanguageName(Language language) {
        String localeLanguageCode = this.localeResolver.resolveLocale(this.httpServletRequest).getLanguage();
        return language.getLanguageName(localeLanguageCode);
    }


}
