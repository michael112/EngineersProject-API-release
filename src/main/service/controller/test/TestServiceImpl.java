package main.service.controller.test;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

import org.joda.time.LocalDate;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.service.crud.course.test.TestCrudService;

import main.service.crud.course.grade.GradeCrudService;

import main.json.course.test.view.TestListJson;

import main.json.course.test.TestJson;
import main.json.course.test.edit.EditTestTitleJson;
import main.json.course.test.edit.EditTestDateJson;
import main.json.course.test.edit.EditTestDescriptionJson;

import main.json.course.CourseUserJson;

import main.model.user.User;
import main.model.course.Test;
import main.model.course.Course;

@Service("testService")
public class TestServiceImpl extends AbstractService implements TestService {

    private TestCrudService testCrudService;

    private GradeCrudService gradeCrudService;

    private DateTimeFormatter dateFormat;

    public TestListJson getTestList(User user, Course course) {
        try {
            TestListJson result = new TestListJson(course.getId(), course.getLanguage().getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), course.getCourseLevel().getId(), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
            for( User teacher : course.getTeachers() ) {
                result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
            }

            String dateStr;
            for( Test test : course.getTests() ) {
                dateStr = this.dateFormat.print(test.getDate());
                if( test.containsTestSolution(user) && ( test.getTestSolution(user).getGrade() != null ) ) {
                    if( ( test.getDescription() != null ) && ( !( test.getDescription().equals("") )) ) {
                        switch( test.getTestSolution(user).getGrade().getGrade().getScale() ) {
                            case PUNKTOWA:
                                result.addTest(new main.json.course.test.view.TestJson(test.getId(), test.getTitle(),  dateStr, test.getDescription(), test.getTestSolution(user).isWritten(), true, test.getTestSolution(user).getGrade().getGradeValue(), test.getTestSolution(user).getGrade().getGrade().getMaxPoints(), test.getTestSolution(user).getGrade().getGrade().getScale().name()));
                                break;
                            case SZKOLNA:
                                result.addTest(new main.json.course.test.view.TestJson(test.getId(), test.getTitle(),  dateStr, test.getDescription(), test.getTestSolution(user).isWritten(), true, test.getTestSolution(user).getGrade().getGradeValue(), test.getTestSolution(user).getGrade().getGrade().getScale().name()));
                                break;
                        }
                    }
                    else {
                        switch( test.getTestSolution(user).getGrade().getGrade().getScale() ) {
                            case PUNKTOWA:
                                result.addTest(new main.json.course.test.view.TestJson(test.getId(), test.getTitle(),  dateStr, test.getTestSolution(user).isWritten(), true, test.getTestSolution(user).getGrade().getGradeValue(), test.getTestSolution(user).getGrade().getGrade().getMaxPoints(), test.getTestSolution(user).getGrade().getGrade().getScale().name()));
                                break;
                            case SZKOLNA:
                                result.addTest(new main.json.course.test.view.TestJson(test.getId(), test.getTitle(),  dateStr, test.getTestSolution(user).isWritten(), true, test.getTestSolution(user).getGrade().getGradeValue(), test.getTestSolution(user).getGrade().getGrade().getScale().name()));
                                break;
                        }
                    }
                }
                else if( ( test.getDescription() != null ) && ( !( test.getDescription().equals("") )) ) {
                    result.addTest(new main.json.course.test.view.TestJson(test.getId(), test.getTitle(),  dateStr, test.getDescription(), false, false));
                }
                else {
                    result.addTest(new main.json.course.test.view.TestJson(test.getId(), test.getTitle(),  dateStr, false, false));
                }
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void addTest(Course course, TestJson testJson) {
        try {
            LocalDate testDate = this.dateFormat.parseLocalDate(testJson.getDate());
            Test test = new Test(testJson.getTitle(), testDate, testJson.getDescription(), course);
            this.testCrudService.saveTest(test);
        }
        catch( IllegalArgumentException ex ) {
            throw(ex);
        }
        catch( UnsupportedOperationException | NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editTest(Test test, TestJson testJson) {
        try {
            LocalDate testDate = this.dateFormat.parseLocalDate(testJson.getDate());
            test.setTitle(testJson.getTitle());
            test.setDate(testDate);
            test.setDescription(testJson.getDescription());
            this.testCrudService.updateTest(test);
        }
        catch( IllegalArgumentException ex ) {
            throw(ex);
        }
        catch( UnsupportedOperationException | NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editTestTitle(Test test, EditTestTitleJson editTestTitleJson){
        try {
            test.setTitle(editTestTitleJson.getTitle());
            this.testCrudService.updateTest(test);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editTestDate(Test test, EditTestDateJson editTestDateJson){
        try {
            LocalDate testDate = this.dateFormat.parseLocalDate(editTestDateJson.getDate());
            test.setDate(testDate);
            this.testCrudService.updateTest(test);
        }
        catch( IllegalArgumentException ex ) {
            throw(ex);
        }
        catch( UnsupportedOperationException | NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editTestDescription(Test test, EditTestDescriptionJson editTestDescriptionJson){
        try {
            test.setDescription(editTestDescriptionJson.getDescription());
            this.testCrudService.updateTest(test);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void removeTest(Course course, Test test) {
        course.removeTest(test);
        if( test.getGrade() != null ) this.gradeCrudService.deleteGrade(test.getGrade());
        this.testCrudService.deleteTest(test);
    }

    @Autowired
    public TestServiceImpl(LocaleCodeProvider localeCodeProvider, TestCrudService testCrudService, GradeCrudService gradeCrudService) {
        super(localeCodeProvider);
        this.testCrudService = testCrudService;
        this.gradeCrudService = gradeCrudService;
        this.dateFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
    }

}
