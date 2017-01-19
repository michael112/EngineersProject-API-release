package main.service.controller.course.attachement;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.multipart.MultipartFile;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

import org.springframework.dao.DataIntegrityViolationException;

import main.util.locale.LocaleCodeProvider;

import main.service.file.FileUploadService;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.file.FileCrudService;

import main.service.controller.AbstractService;

import main.json.course.attachements.FileInfoListJson;

import main.json.course.attachements.FileInfoJson;
import main.json.course.CourseUserJson;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.File;

@Service("courseAttachementService")
public class CourseAttachementServiceImpl extends AbstractService implements CourseAttachementService {

    private FileUploadService fileUploadService;

    private CourseCrudService courseCrudService;

    private FileCrudService fileCrudService;

    private DateTimeFormatter dateFormat;

    public FileInfoListJson getAttachementList(Course course) {
        FileInfoListJson result = new FileInfoListJson(course.getId(), course.getLanguage().getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
        for( User teacher : course.getTeachers() ) {
            result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        for( File attachement : course.getAttachements() ) {
            result.addAttachement(new FileInfoJson(attachement.getId(), attachement.getName(), this.dateFormat.print(attachement.getDate()), attachement.getPath(), attachement.getSender().getId(), attachement.getSender().getFullName()));
        }
        return result;
    }

    public void addAttachement(User sender, Course course, MultipartFile attachement) {
        File fileInfo = this.fileUploadService.uploadFile(attachement, sender);
        course.addAttachement(fileInfo);
        this.courseCrudService.updateCourse(course);
    }

    public void removeAttachement(Course course, File attachement, boolean fullRemove) {
        if( course.containsAttachement(attachement) ) {
            course.removeAttachement(attachement);
            this.courseCrudService.updateCourse(course);
        }
        if( fullRemove ) {
            try {
                this.fileCrudService.deleteFile(attachement);
            }
            catch( DataIntegrityViolationException ex ) {}
        }
    }

    @Autowired
    public CourseAttachementServiceImpl(LocaleCodeProvider localeCodeProvider, FileUploadService fileUploadService, CourseCrudService courseCrudService, FileCrudService fileCrudService) {
        super(localeCodeProvider);
        this.fileUploadService = fileUploadService;
        this.courseCrudService = courseCrudService;
        this.fileCrudService = fileCrudService;
        this.dateFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
    }

}
