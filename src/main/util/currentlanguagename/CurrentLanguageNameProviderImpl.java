package main.util.currentlanguagename;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import main.model.language.Language;

@Service("currentLanguageNameProvider")
public class CurrentLanguageNameProviderImpl implements CurrentLanguageNameProvider {

    private HttpServletRequest httpServletRequest;

    private LocaleResolver localeResolver;

    public String getLanguageName(Language language) {
        String localeLanguageCode = this.localeResolver.resolveLocale(this.httpServletRequest).getLanguage();
        return language.getLanguageName(localeLanguageCode);
    }

    @Autowired
    public CurrentLanguageNameProviderImpl(LocaleResolver localeResolver, HttpServletRequest httpServletRequest) {
        this.localeResolver = localeResolver;
        this.httpServletRequest = httpServletRequest;
    }
}
