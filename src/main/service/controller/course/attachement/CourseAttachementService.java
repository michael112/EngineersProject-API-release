package main.service.controller.course.attachement;

import org.springframework.web.multipart.MultipartFile;

import main.json.course.attachements.FileInfoListJson;

import main.json.course.attachements.NewRemoteAttachementJson;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.File;

public interface CourseAttachementService {

    FileInfoListJson getAttachementList(Course course);

    void addLocalAttachement(User sender, Course course, MultipartFile attachement);

    void addRemoteAttachement(User sender, Course course, NewRemoteAttachementJson attachement);

    void removeAttachement(Course course, File attachement, boolean fullRemove);

}
