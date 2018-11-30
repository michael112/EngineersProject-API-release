package main.util.dropbox.client;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.DbxException;

import main.util.properties.PropertyProvider;

@Service("dropboxClientUtils")
public class DropboxClientUtilsImpl implements DropboxClientUtils {

    @Autowired
    private PropertyProvider propertyProvider;

    public DbxClientV2 createDropboxClient() throws DbxException {
        DbxRequestConfig config = DbxRequestConfig.newBuilder(this.propertyProvider.getProperty("dropbox.app.name")).build();
        DbxClientV2 client = new DbxClientV2(config, this.propertyProvider.getProperty("dropbox.access.token"));
        return client;
    }

}
