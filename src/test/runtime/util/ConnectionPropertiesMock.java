package test.runtime.util;

import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import java.sql.Driver;

public class ConnectionPropertiesMock implements ConnectionProperties {

    public void setDriverClass(Class<? extends Driver> driverClass) {}

    public void setPassword(String password) {}

    public void setUrl(String url) {}

    public void setUsername(String username) {}

}
