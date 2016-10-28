package main.service.controller.course.attachement;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import org.springframework.web.multipart.MultipartFile;

import main.service.controller.AbstractService;

import main.json.course.attachements.FileInfoListJson;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.File;

@Service("courseAttachementService")
public class CourseAttachementServiceImpl extends AbstractService implements CourseAttachementService {

    public FileInfoListJson getAttachementList(Course course) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void addAttachement(User sender, Course course, MultipartFile attachement) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void removeAttachement(Course course, File attachement, boolean fullRemove) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @Autowired
    public CourseAttachementServiceImpl(LocaleCodeProvider localeCodeProvider) {
        super(localeCodeProvider);
    }

}
