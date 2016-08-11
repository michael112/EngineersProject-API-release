package main.service.localetolanguage;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.LocaleResolver;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import main.model.language.Language;

@Service("localeToLanguageService")
public class LocaleToLanguageService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private LocaleResolver localeResolver;

    public String getLanguageName(Language language) {
        String localeLanguageCode = this.localeResolver.resolveLocale(this.httpServletRequest).getLanguage();
        return language.getLanguageName(localeLanguageCode);
    }

}
