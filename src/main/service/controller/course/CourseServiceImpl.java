package main.service.controller.course;

import java.util.Set;
import java.util.HashSet;

import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import main.service.controller.AbstractService;

import main.service.crud.course.coursetype.CourseTypeCrudService;
import main.service.crud.language.LanguageCrudService;

import main.util.locale.LocaleCodeProvider;

import main.model.course.Course;
import main.model.course.CourseDay;
import main.model.course.CourseType;
import main.model.course.CourseMembership;
import main.model.course.Homework;
import main.model.course.Test;
import main.model.course.Message;

import main.model.language.Language;

import main.model.user.User;

import main.json.course.AvailableLngAndTypesJson;
import main.json.course.CourseListJson;
import main.json.course.CourseUserJson;
import main.json.course.CourseInfoTeacherJson;
import main.json.course.CourseInfoStudentJson;
import main.json.course.NextLessonJson;
import main.json.course.HomeworkJson;
import main.json.course.TestJson;
import main.json.course.MessageJson;

@Service("courseService")
public class CourseServiceImpl extends AbstractService implements CourseService {

    private CourseTypeCrudService courseTypeCrudService;

    private LanguageCrudService languageCrudService;

    public CourseListJson getCourseStudentList(Course course, String currentLocale) {
        if( course == null ) throw new IllegalArgumentException();
        String languageName = course.getLanguage().getLanguageName(currentLocale);
        String courseTypeName = course.getCourseType().getCourseTypeName(currentLocale);
        CourseListJson result = new CourseListJson(course.getId(), languageName, course.getCourseLevel().getName(), course.getCourseType().getId(), courseTypeName);
        for( CourseMembership studentMembership : course.getStudents() ) {
            User student = studentMembership.getUser();
            result.addStudent(new CourseUserJson(student.getId(), student.getFullName()));
        }
        for( User teacher : course.getTeachers() ) {
            result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        return result;
    }

    public CourseInfoStudentJson getCourseInfoStudent(Course course, User user) {
        String languageName = course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode());
        String courseTypeName = course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode());
        CourseInfoStudentJson courseInfo = new CourseInfoStudentJson(course.getId(), languageName, course.getCourseLevel().getName(), course.getCourseType().getId(), courseTypeName, getNextLesson(course));
        for( User teacher : course.getTeachers() ) {
            courseInfo.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        Set<Homework> incomingHomeworks = getIncomingHomeworks(course.getHomeworks(), user);
        Set<Test> incomingTests = getIncomingTests(course.getTests());
        Set<Message> teacherMessages = getTeacherMessages(course, user);
        for( Homework homework : incomingHomeworks ) {
            courseInfo.addIncomingHomework(new HomeworkJson(homework.getId(), homework.getDate().toString(), homework.getTitle()));
        }
        for( Test test : incomingTests ) {
            courseInfo.addIncomingTest(new TestJson(test.getId(), test.getDate().toString(), test.getTitle()));
        }
        for( Message message : teacherMessages ) {
            courseInfo.addTeacherMessage(new MessageJson(message.getId(), message.getTitle()));
        }
        return courseInfo;
    }

    public CourseInfoTeacherJson getCourseInfoTeacher(Course course, User user) {
        String languageName = course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode());
        String courseTypeName = course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode());
        CourseInfoTeacherJson courseInfo = new CourseInfoTeacherJson(course.getId(), languageName, course.getCourseLevel().getName(), course.getCourseType().getId(), courseTypeName, getNextLesson(course));
        for( User teacher : course.getTeachers() ) {
            courseInfo.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        Set<Test> incomingTests = getIncomingTests(course.getTests());
        Set<Message> teacherMessages = getTeacherMessages(course, user);
        for( Test test : incomingTests ) {
            courseInfo.addIncomingTest(new TestJson(test.getId(), test.getDate().toString(), test.getTitle()));
        }
        for( Message message : teacherMessages ) {
            courseInfo.addTeacherMessage(new MessageJson(message.getId(), message.getTitle()));
        }
        return courseInfo;
    }

    public AvailableLngAndTypesJson showAvailableLanguagesAndCourseTypes() {
        AvailableLngAndTypesJson result = new AvailableLngAndTypesJson();
        Set<Language> availableLanguages = this.languageCrudService.findAllLanguages();
        Set<CourseType> availableCourseTypes = this.courseTypeCrudService.findAllCourseTypes();
        for( Language language : availableLanguages ) {
            result.addLanguage(language.getId(), language.getLanguageName(this.localeCodeProvider.getLocaleCode()));
        }
        for( CourseType courseType : availableCourseTypes ) {
            result.addType(courseType.getId(), courseType.getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
        }
        return result;
    }

    private NextLessonJson getNextLesson(Course course) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        java.util.Date today = calendar.getTime();
        calendar.setTime(today);

        java.util.Date resultDate = null;
        String resultHour = null;

        for(CourseDay courseDay : course.getCourseDays()) {
            while ((calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1) != courseDay.getDay().getDay()) {
                calendar.add(java.util.Calendar.DATE, 1);
            }
            if( ( resultDate == null ) || ( calendar.getTime().before(resultDate) ) ) {
                resultDate = calendar.getTime();
                resultHour = courseDay.getHourFrom().getTime();
            }
        }
        try {
            calendar.setTime(resultDate);
            String resultDateStr = resultDate == null ? null : String.valueOf(calendar.get(java.util.Calendar.YEAR)) + '-' + String.valueOf(calendar.get(java.util.Calendar.MONTH)) + '-' + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

            NextLessonJson result = new NextLessonJson(resultDateStr, resultHour);

            return result;
        }
        catch( NullPointerException ex ) {
            return null;
        }
    }

    private Set<Homework> getIncomingHomeworks(Set<Homework> allHomeworks, User user) {
        Set<Homework> incomingHomeworks = new HashSet<>();
        for( Homework homework : allHomeworks ) {
            if( !(homework.containsHomeworkSolution(user)) ) {
                incomingHomeworks.add(homework);
            }
        }
        return incomingHomeworks;
    }

    private Set<Test> getIncomingTests(Set<Test> allTests) {
        Set<Test> incomingTests = new HashSet<>();

        java.util.Calendar calendar = java.util.Calendar.getInstance();

        for( Test test : allTests ) {
            if( calendar.getTime().before(test.getDate()) ){
                incomingTests.add(test);
            }
        }

        return incomingTests;
    }

    private Set<Message> getTeacherMessages(Course course, User user) {
        Set<Message> result = new HashSet<>();
        for( Message message : course.getMessages() ) {
            if( ( course.containsTeacher(message.getSender()) ) && ( ( message.getSender().equals(user) ) || ( message.containsReceiver(user) ) ) ) {
                result.add(message);
            }
        }
        return result;
    }

    @Autowired
    public CourseServiceImpl(LocaleCodeProvider localeCodeProvider, CourseTypeCrudService courseTypeCrudService, LanguageCrudService languageCrudService) {
        super(localeCodeProvider);
        this.courseTypeCrudService = courseTypeCrudService;
        this.languageCrudService = languageCrudService;
    }
}
