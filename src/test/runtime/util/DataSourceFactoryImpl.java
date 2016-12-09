package test.runtime.util;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;

public class DataSourceFactoryImpl implements DataSourceFactory {

    private DataSource dataSource;

    public ConnectionProperties getConnectionProperties() {
        return new ConnectionPropertiesMock();
    }

    public final DataSource getDataSource() {
        return this.dataSource;
    }

    @Autowired
    public DataSourceFactoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
