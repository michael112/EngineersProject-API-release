package main.service.file;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dropbox.core.v2.DbxClientV2;

import main.util.dropbox.client.DropboxClientUtils;

@Service("dropboxRemoteDownloadService")
public class DropboxRemoteDownloadServiceImpl implements RemoteDownloadService {

    @Autowired
    private DropboxClientUtils dropboxClientUtils;

    public byte[] getFile(String remoteID) throws Exception {
        DbxClientV2 dropboxClient = this.dropboxClientUtils.createDropboxClient();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        dropboxClient.files().download(remoteID).download(outputStream);
        return outputStream.toByteArray();
    }

}
