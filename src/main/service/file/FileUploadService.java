package main.service.file;

import org.springframework.web.multipart.MultipartFile;

import main.model.course.File;
import main.model.user.User;

public interface FileUploadService {

    File uploadFile(MultipartFile file, User sender);

}
