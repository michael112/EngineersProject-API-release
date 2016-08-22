package main.util.labels;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.context.MessageSource;

import lombok.Setter;

public class LabelProviderImpl implements LabelProvider {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Setter
    private LocaleResolver localeResolver;
    @Setter
    private MessageSource messageSource;

    public String getLabel(String identifier) {
        String resultLabel = messageSource.getMessage(identifier, null, this.localeResolver.resolveLocale(this.httpServletRequest));
        if( resultLabel.startsWith("\"") && resultLabel.endsWith("\"") ) {
            resultLabel = resultLabel.substring(1, resultLabel.length()-1);
        }
        return resultLabel;
    }

}
