package main.service.controller.grade;

import main.service.controller.AbstractService;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.crud.user.user.UserCrudService;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.grade.GradeCrudService;

import main.service.crud.course.homework.HomeworkCrudService;
import main.service.crud.course.test.TestCrudService;

import main.model.course.Grade;
import main.model.course.Homework;
import main.model.course.Test;
import main.model.course.StudentGrade;
import main.model.course.Course;
import main.model.course.CourseMembership;
import main.model.user.User;

import main.json.course.CourseUserJson;
import main.json.course.HomeworkJson;
import main.json.course.TestJson;

import main.json.course.grade.commons.CourseJson;
import main.json.course.grade.commons.GradeJson;

import main.json.course.grade.commons.NewGradeJson;

import main.json.course.grade.teacher.edit.EditFullGradeJson;
import main.json.course.grade.teacher.edit.EditGradeInfoJson;
import main.json.course.grade.teacher.edit.EditScaleJson;
import main.json.course.grade.teacher.edit.EditPointsJson;
import main.json.course.grade.teacher.edit.StudentGradeJson;

@Service("gradeService")
public class GradeServiceImpl extends AbstractService implements GradeService {

    private UserCrudService userCrudService;

    private CourseCrudService courseCrudService;

    private GradeCrudService gradeCrudService;

    private HomeworkCrudService homeworkCrudService;

    private TestCrudService testCrudService;

    public main.json.course.grade.student.allgrades.list.GradeListJson getStudentGradeList(User student, Course course) throws IllegalArgumentException {
        if( ( student == null ) || ( course == null ) ) throw new IllegalArgumentException();
        main.json.course.grade.student.allgrades.list.GradeListJson result = new main.json.course.grade.student.allgrades.list.GradeListJson(new CourseUserJson(student.getId(), student.getFullName()), getCourseJson(course, this.localeCodeProvider.getLocaleCode()));
        for( Grade grade : course.getGrades() ) {
            if( grade.containsGradeForUser(student) ) {
                result.addGrade(getGradeInfo(grade));
            }
        }
        return result;
    }

    private CourseJson getCourseJson(Course course, String languageCode) {
        CourseJson result = new CourseJson(course.getId(), course.getLanguage().getLanguageName(languageCode), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(languageCode));
        for( User teacher : course.getTeachers() ) {
            result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        return result;
    }

    public main.json.course.grade.teacher.allgrades.list.GradeListJson getTeacherGradeList(Course course) throws IllegalArgumentException {
        if( course == null ) throw new IllegalArgumentException();
        main.json.course.grade.teacher.allgrades.list.GradeListJson result = new main.json.course.grade.teacher.allgrades.list.GradeListJson(getCourseJson(course, this.localeCodeProvider.getLocaleCode()));
        for( Grade grade : course.getGrades() ) {
            result.addGrade(getGradeInfo(grade));
        }
        return result;
    }

    public GradeJson getGradeInfo(Grade grade) {
        if( grade == null ) throw new IllegalArgumentException();
        GradeJson gradeJson = new GradeJson(grade.getId(), new CourseUserJson(grade.getGradedBy().getId(), grade.getGradedBy().getFullName()), grade.getGradeTitle(), grade.getGradeDescription(), grade.getScale().name(), grade.getMaxPoints(), grade.getWeight());
        if( grade.hasHomework() ) {
            gradeJson.setHomeworkFor(new HomeworkJson(grade.getTask().getId(), grade.getTask().getDate().toString(), grade.getTask().getTitle()));
        }
        if( grade.hasTest() ) {
            gradeJson.setTestFor(new TestJson(grade.getTask().getId(), grade.getTask().getDate().toString(), grade.getTask().getTitle()));
        }
        for( StudentGrade studentGrade : grade.getGrades() ) {
            gradeJson.addGrade(new main.json.course.grade.commons.StudentGradeJson(studentGrade.getId(), studentGrade.getGradeValue(), new CourseUserJson(studentGrade.getStudent().getUser().getId(), studentGrade.getStudent().getUser().getFullName())));
        }
        return gradeJson;
    }

    public void createNewGrade(NewGradeJson newGradeJson) {
        try {
            User gradedBy = this.userCrudService.findUserByID(newGradeJson.getGradedByID());
            Course course = this.courseCrudService.findCourseByID(newGradeJson.getCourseID());
            Grade grade = new Grade(gradedBy, course, newGradeJson.getGradeTitle(), newGradeJson.getGradeDescription(), newGradeJson.getScale(), newGradeJson.getMaxPoints(), newGradeJson.getWeight());
            if( newGradeJson.hasHomework() ) {
                Homework homework = this.homeworkCrudService.findHomeworkByID(newGradeJson.getHomeworkID());
                grade.setTask(homework);
            }
            if( newGradeJson.hasTest() ) {
                Test test = this.testCrudService.findTestByID(newGradeJson.getTestID());
                grade.setTask(test);
            }
            this.gradeCrudService.saveGrade(grade);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editGrade(EditFullGradeJson editGradeJson) {
        try {
            Grade gradeToEdit = this.gradeCrudService.findGradeByID(editGradeJson.getGradeID());
            gradeToEdit.setGradeTitle(editGradeJson.getGradeTitle());
            gradeToEdit.setGradeDescription(editGradeJson.getGradeDescription());
            gradeToEdit.setScale(editGradeJson.getScale());
            gradeToEdit.setMaxPoints(editGradeJson.getMaxPoints());
            gradeToEdit.setWeight(editGradeJson.getWeight());
            this.gradeCrudService.updateGrade(gradeToEdit);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editGrade(EditGradeInfoJson editGradeJson) {
        try {
            Grade gradeToEdit = this.gradeCrudService.findGradeByID(editGradeJson.getGradeID());
            gradeToEdit.setGradeTitle(editGradeJson.getGradeTitle());
            gradeToEdit.setGradeDescription(editGradeJson.getGradeDescription());
            this.gradeCrudService.updateGrade(gradeToEdit);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editGrade(EditPointsJson editGradeJson) {
        try {
            Grade gradeToEdit = this.gradeCrudService.findGradeByID(editGradeJson.getGradeID());
            gradeToEdit.setMaxPoints(editGradeJson.getMaxPoints());
            gradeToEdit.setWeight(editGradeJson.getWeight());
            this.gradeCrudService.updateGrade(gradeToEdit);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editGrade(EditScaleJson editGradeJson) {
        try {
            Grade gradeToEdit = this.gradeCrudService.findGradeByID(editGradeJson.getGradeID());
            gradeToEdit.setScale(editGradeJson.getScale());
            this.gradeCrudService.updateGrade(gradeToEdit);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void createStudentGrade(StudentGradeJson newStudentGradeJson) {
        try {
            Grade gradeToEdit = this.gradeCrudService.findGradeByID(newStudentGradeJson.getGradeID());
            CourseMembership courseMembership = gradeToEdit.getCourse().getCourseMembership(this.userCrudService.findUserByID(newStudentGradeJson.getStudentID()));
            StudentGrade newStudentGrade = new StudentGrade(courseMembership, newStudentGradeJson.getGrade(), gradeToEdit);
            this.gradeCrudService.updateGrade(gradeToEdit);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editStudentGrade(StudentGradeJson editStudentGradeJson) {
        try {
            Grade gradeToEdit = this.gradeCrudService.findGradeByID(editStudentGradeJson.getGradeID());
            StudentGrade studentGradeToEdit = gradeToEdit.getGradeForUser(this.userCrudService.findUserByID(editStudentGradeJson.getStudentID()));
            studentGradeToEdit.setGradeValue(editStudentGradeJson.getGrade());
            this.gradeCrudService.updateGrade(gradeToEdit);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    @Autowired
    public GradeServiceImpl(LocaleCodeProvider localeCodeProvider, UserCrudService userCrudService, CourseCrudService courseCrudService, GradeCrudService gradeCrudService, HomeworkCrudService homeworkCrudService, TestCrudService testCrudService) {
        super(localeCodeProvider);
        this.userCrudService = userCrudService;
        this.courseCrudService = courseCrudService;
        this.gradeCrudService = gradeCrudService;
        this.homeworkCrudService = homeworkCrudService;
        this.testCrudService = testCrudService;
    }
}
