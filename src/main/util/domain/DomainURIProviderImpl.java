package main.util.domain;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service("domainURIProvider")
public class DomainURIProviderImpl implements DomainURIProvider {

    @Autowired
    private HttpServletRequest httpServletRequest;

    public String getDomainURI() {
        String currentURI = this.httpServletRequest.getRequestURL().toString();

        String http = "http://";
        String[] parts;

        if( currentURI.startsWith(http) ) {
            parts = currentURI.substring(http.length()).split("/");
        }
        else {
            parts = currentURI.split("/");
        }

        String domainURI = http + parts[0];
        return domainURI + this.httpServletRequest.getContextPath();
    }

}
