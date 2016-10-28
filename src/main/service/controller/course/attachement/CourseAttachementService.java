package main.service.controller.course.attachement;

import main.json.course.attachements.FileInfoListJson;

import org.springframework.web.multipart.MultipartFile;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.File;

public interface CourseAttachementService {

    FileInfoListJson getAttachementList(Course course);

    void addAttachement(User sender, Course course, MultipartFile attachement);

    void removeAttachement(Course course, File attachement, boolean fullRemove);

}
