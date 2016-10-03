package main.util.locale;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;

public class LocaleCodeProviderImpl implements LocaleCodeProvider {

    private HttpServletRequest httpServletRequest;

    private LocaleResolver localeResolver;

    public String getLocaleCode() {
        return this.localeResolver.resolveLocale(this.httpServletRequest).getLanguage();
    }

    public LocaleCodeProviderImpl(LocaleResolver localeResolver, HttpServletRequest httpServletRequest) {
        this.localeResolver = localeResolver;
        this.httpServletRequest = httpServletRequest;
    }
}
