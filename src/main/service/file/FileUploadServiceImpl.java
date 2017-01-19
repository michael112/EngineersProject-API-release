package main.service.file;

import org.joda.time.DateTime;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import main.util.labels.LabelProvider;
import main.util.properties.PropertyProvider;

import main.model.course.File;
import main.model.user.User;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private PropertyProvider propertyProvider;

    @Autowired
    private LabelProvider labelProvider;

    public File uploadFile(MultipartFile file, User sender) {
        try {
            String directory = this.propertyProvider.getProperty("file.upload.path");
            File result = new File();
            result.setName(file.getOriginalFilename());
            result.setPath(directory);
            result.setDate(new DateTime(new Timestamp(System.currentTimeMillis())));
            result.setSender(sender);
            try {
                file.transferTo(new java.io.File(directory + result.getId() + getExtension(file)));
                return result;
            }
            catch( Exception ex ) {
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
