package main.service.file;

import org.joda.time.DateTime;

import java.io.BufferedInputStream;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import main.util.labels.LabelProvider;
import main.util.properties.PropertyProvider;

import main.util.dropbox.client.DropboxClientUtils;

import main.service.crud.course.file.FileCrudService;

import main.model.course.File;
import main.model.user.User;

@Service("dropboxFileUploadService")
public class DropboxFileUploadServiceImpl implements FileUploadService {

    @Autowired
    private PropertyProvider propertyProvider;

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private FileCrudService fileCrudService;

    @Autowired
    private DropboxClientUtils dropboxClientUtils;

    public File uploadFile(MultipartFile file, User sender) {
        try {
            DbxClientV2 dropboxClient = this.dropboxClientUtils.createDropboxClient();

            File result = new File();
            result.setName(file.getOriginalFilename());
            result.setDate(new DateTime(new Timestamp(System.currentTimeMillis())));
            result.setSender(sender);
            result.setRemote(true);

            try {
                this.fileCrudService.saveFile(result);
                String directory = this.propertyProvider.getProperty("dropbox.directory") + result.getId() + getExtension(file);

                FileMetadata fileMetadata = dropboxClient.files().uploadBuilder(directory).uploadAndFinish(new BufferedInputStream(file.getInputStream()));

                result.setRemoteID(fileMetadata.getId());
                this.fileCrudService.updateFile(result);

                return result;
            }
            catch( Exception ex ) {
                this.fileCrudService.deleteFile(result);
                throw new IllegalArgumentException(this.labelProvider.getLabel("error.uploadfile"));
            }
        }
        catch( Exception ex ) {
            throw new IllegalArgumentException(this.labelProvider.getLabel("error.uploadfile"));
        }
    }

    private String getExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        int indexOfDot = fileName.lastIndexOf('.');
        return indexOfDot != -1 ? fileName.substring(indexOfDot) : "";
    }

}
