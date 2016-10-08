package main.util.locale;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service("localeCodeProvider")
public class LocaleCodeProviderImpl implements LocaleCodeProvider {

    private HttpServletRequest httpServletRequest;

    private LocaleResolver localeResolver;

    public String getLocaleCode() {
        return this.localeResolver.resolveLocale(this.httpServletRequest).getLanguage();
    }

    @Autowired
    public LocaleCodeProviderImpl(LocaleResolver localeResolver, HttpServletRequest httpServletRequest) {
        this.localeResolver = localeResolver;
        this.httpServletRequest = httpServletRequest;
    }
}
