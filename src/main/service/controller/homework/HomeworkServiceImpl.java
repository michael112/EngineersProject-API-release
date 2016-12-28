package main.service.controller.homework;

import java.util.List;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.multipart.MultipartFile;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.service.file.FileUploadService;

import main.service.crud.course.homework.HomeworkCrudService;

import main.json.course.HomeworkJson;

import main.json.course.CourseUserJson;
import main.json.course.CourseJson;

import main.json.course.homework.list.HomeworkListTeacherJson;
import main.json.course.homework.list.HomeworkListStudentJson;

import main.json.course.homework.info.HomeworkInfoStudentJson;
import main.json.course.homework.info.HomeworkInfoTeacherJson;

import main.json.course.homework.NewHomeworkJson;

import main.json.course.homework.HomeworkWithGradeJson;
import main.json.course.homework.HomeworkGradeJson;

import main.json.course.homework.edit.EditHomeworkTitleJson;
import main.json.course.homework.edit.EditHomeworkDateJson;
import main.json.course.homework.edit.EditHomeworkDescriptionJson;

import main.json.course.homework.info.solution.HomeworkSolutionJson;

import main.json.AttachementJson;

import main.model.course.Course;
import main.model.course.StudentGrade;
import main.model.course.Homework;
import main.model.course.HomeworkSolution;

import main.model.course.File;
import main.model.user.User;

@Service("homeworkService")
public class HomeworkServiceImpl extends AbstractService implements HomeworkService {

    private FileUploadService fileUploadService;

    private HomeworkCrudService homeworkCrudService;

    private DateTimeFormatter dateFormat;

    public HomeworkListTeacherJson getHomeworkListTeacher(Course course) {
        HomeworkListTeacherJson result = new HomeworkListTeacherJson(course.getId(), course.getLanguage().getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
        for( User teacher : course.getTeachers() ) {
            result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        for( Homework homework : course.getHomeworks() ) {
            result.addHomework(new HomeworkJson(homework.getId(), this.dateFormat.print(homework.getDate()), homework.getTitle()));
        }
        return result;
    }

    public HomeworkListStudentJson getHomeworkListStudent(Course course, User student) {
        HomeworkListStudentJson result = new HomeworkListStudentJson(course.getId(), course.getLanguage().getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
        for( User teacher : course.getTeachers() ) {
            result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        for( Homework homework : course.getHomeworks() ) {
            HomeworkGradeJson homeworkGrade = null;
            if( homework.containsHomeworkSolution(student) ) {
                HomeworkSolution homeworkSolution = homework.getHomeworkSolution(student);
                homeworkGrade = new HomeworkGradeJson(homeworkSolution.getGrade().getGrade().getScale().name(), homeworkSolution.getGrade().getGradeValue(), homeworkSolution.getGrade().getGrade().getWeight(), homeworkSolution.getGrade().getGrade().getMaxPoints());
            }
            result.addHomework(new HomeworkWithGradeJson(homework.getId(), this.dateFormat.print(homework.getDate()), homework.getTitle(), homeworkGrade));
        }
        return result;
    }

    public HomeworkInfoTeacherJson getHomeworkInfoTeacher(Homework homework) {
        CourseJson courseJson = new CourseJson(homework.getCourse().getId(), homework.getCourse().getLanguage().getId(), homework.getCourse().getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), homework.getCourse().getCourseLevel().getName(), homework.getCourse().getCourseType().getId(), homework.getCourse().getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
        for( User teacher : homework.getCourse().getTeachers() ) {
            courseJson.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        HomeworkInfoTeacherJson result = new HomeworkInfoTeacherJson(courseJson, homework.getId(), this.dateFormat.print(homework.getDate()), homework.getTitle(), homework.getDescription());
        for( File attachement : homework.getAttachements() ) {
            result.addAttachement(new AttachementJson(attachement.getId(), attachement.getName(), this.dateFormat.print(attachement.getDate()), attachement.getPath()));
        }
        for( HomeworkSolution solution : homework.getHomeworkSolutions() ) {
            result.addSolution(new HomeworkSolutionJson(solution.getUser().getId(), solution.getUser().getFullName(), new AttachementJson(solution.getSolutionFile().getId(), solution.getSolutionFile().getName(), this.dateFormat.print(solution.getSolutionFile().getDate()), solution.getSolutionFile().getPath())));
        }
        return result;
    }

    public HomeworkInfoStudentJson getHomeworkInfoStudent(Homework homework, User student) {
        CourseJson courseJson = new CourseJson(homework.getCourse().getId(), homework.getCourse().getLanguage().getId(), homework.getCourse().getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), homework.getCourse().getCourseLevel().getName(), homework.getCourse().getCourseType().getId(), homework.getCourse().getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
        for( User teacher : homework.getCourse().getTeachers() ) {
            courseJson.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        HomeworkInfoStudentJson result;
        if( homework.containsHomeworkSolution(student) ) {
            HomeworkSolution solution = homework.getHomeworkSolution(student);
            AttachementJson solutionFileJson = new AttachementJson(solution.getSolutionFile().getId(), solution.getSolutionFile().getName(), this.dateFormat.print(solution.getSolutionFile().getDate()), solution.getSolutionFile().getPath());
            if( solution.getGrade() != null ) {
                StudentGrade solutionGrade = solution.getGrade();
                result = new HomeworkInfoStudentJson(courseJson, homework.getId(), this.dateFormat.print(homework.getDate()), homework.getTitle(), homework.getDescription(), solutionFileJson, new HomeworkGradeJson(solutionGrade.getGrade().getScale().name(), solutionGrade.getGradeValue(), solutionGrade.getGrade().getWeight(), solutionGrade.getGrade().getMaxPoints()));
            }
            else {
                result = new HomeworkInfoStudentJson(courseJson, homework.getId(), this.dateFormat.print(homework.getDate()), homework.getTitle(), homework.getDescription(), solutionFileJson);
            }
        }
        else {
            result = new HomeworkInfoStudentJson(courseJson, homework.getId(), this.dateFormat.print(homework.getDate()), homework.getTitle(), homework.getDescription());
        }
        for( File attachement : homework.getAttachements() ) {
            result.addAttachement(new AttachementJson(attachement.getId(), attachement.getName(), this.dateFormat.print(attachement.getDate()), attachement.getPath()));
        }
        return result;
    }

    public void sendHomeworkSolution(User student, Homework homework, MultipartFile solutionFile) {
        HomeworkSolution newHomeworkSolution = new HomeworkSolution(homework.getCourse().getCourseMembership(student), homework, this.fileUploadService.uploadFile(solutionFile, student));
        homework.addHomeworkSolution(newHomeworkSolution);
        this.homeworkCrudService.updateHomework(homework);
	}

    public void addHomework(User teacher, Course course, NewHomeworkJson homework, List<MultipartFile> attachements) {
        try {
            Homework newHomework = new Homework(homework.getTitle(), this.dateFormat.parseLocalDate(homework.getDate()), homework.getDescription(), course);
            for( MultipartFile file : attachements ) {
                newHomework.addAttachement(this.fileUploadService.uploadFile(file, teacher));
            }
            this.homeworkCrudService.saveHomework(newHomework);
        }
        catch( IllegalArgumentException ex ) {
            throw(ex);
        }
        catch( UnsupportedOperationException | NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
	}

    public void editHomeworkTitle(Homework homework, EditHomeworkTitleJson editHomeworkTitleJson) {
        homework.setTitle(editHomeworkTitleJson.getTitle());
        this.homeworkCrudService.updateHomework(homework);
	}

    public void editHomeworkDate(Homework homework, EditHomeworkDateJson editHomeworkDateJson) {
        try {
            homework.setDate(this.dateFormat.parseLocalDate(editHomeworkDateJson.getDate()));
            this.homeworkCrudService.updateHomework(homework);
        }
        catch( IllegalArgumentException ex ) {
            throw(ex);
        }
        catch( UnsupportedOperationException | NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
	}

    public void editHomeworkDescription(Homework homework, EditHomeworkDescriptionJson editHomeworkDescriptionJson) {
        homework.setDescription(editHomeworkDescriptionJson.getDescription());
        this.homeworkCrudService.updateHomework(homework);
	}

    public void editHomeworkAddAttachement(User sender, Homework homework, MultipartFile attachement) {
        homework.addAttachement(this.fileUploadService.uploadFile(attachement, sender));
        this.homeworkCrudService.updateHomework(homework);
	}

    public void editHomeworkRemoveAttachement(Homework homework, File attachement) {
        homework.removeAttachement(attachement);
        this.homeworkCrudService.updateHomework(homework);
	}

    public void removeHomework(Course course, Homework homework) {
        course.removeHomework(homework);
        this.homeworkCrudService.deleteHomework(homework);
	}

    @Autowired
    public HomeworkServiceImpl(LocaleCodeProvider localeCodeProvider, FileUploadService fileUploadService, HomeworkCrudService homeworkCrudService) {
        super(localeCodeProvider);
        this.fileUploadService = fileUploadService;
        this.homeworkCrudService = homeworkCrudService;
        this.dateFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
    }

}
