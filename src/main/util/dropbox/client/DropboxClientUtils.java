package main.util.dropbox.client;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.DbxException;

public interface DropboxClientUtils {

    DbxClientV2 createDropboxClient() throws DbxException;

}
