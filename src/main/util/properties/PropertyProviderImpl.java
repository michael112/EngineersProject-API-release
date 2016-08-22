package main.util.properties;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("propertyUtil")
public class PropertyProviderImpl implements PropertyProvider {
    @Autowired
    private ApplicationContext appContext;

    public String getProperty(String name) {
        Properties properties = this.appContext.getBean("deployProperties", Properties.class); // uses property factory bean name from xml config
        return properties.getProperty(name);
    }
}
