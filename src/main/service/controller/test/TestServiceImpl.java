package main.service.controller.test;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.service.crud.course.test.TestCrudService;

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

    public TestListJson getTestList(User user, Course course) {
        try {
            TestListJson result = new TestListJson(course.getId(), course.getLanguage().getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
            for( User teacher : course.getTeachers() ) {
                result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String dateStr;
            for( Test test : course.getTests() ) {
                dateStr = dateFormat.format(test.getDate());
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
            SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
            Date testDate = parser.parse(testJson.getDate());
            Test test = new Test(testJson.getTitle(), testDate, testJson.getDescription(), course);
            this.testCrudService.saveTest(test);
        }
        catch( ParseException | NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editTest(Test test, TestJson testJson) {
        try {
            SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
            Date testDate = parser.parse(testJson.getDate());
            test.setTitle(testJson.getTitle());
            test.setDate(testDate);
            test.setDescription(testJson.getDescription());
            this.testCrudService.updateTest(test);
        }
        catch( ParseException | NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editTestTitle(Test test, EditTestTitleJson editTestTitleJson){
        try {
            test.setTitle(editTestTitleJson.getTitle());
            this.testCrudService.saveTest(test);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editTestDate(Test test, EditTestDateJson editTestDateJson){
        try {
            SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
            Date testDate = parser.parse(editTestDateJson.getDate());
            test.setDate(testDate);
            this.testCrudService.updateTest(test);
        }
        catch( ParseException | NullPointerException ex ) {
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

    @Autowired
    public TestServiceImpl(LocaleCodeProvider localeCodeProvider, TestCrudService testCrudService) {
        super(localeCodeProvider);
        this.testCrudService = testCrudService;
    }

}
