package main.controllers;

import java.util.Set;
import java.util.HashSet;

import java.util.Calendar;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.annotation.PostConstruct;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.util.Assert;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.CourseControllerUrlConstants;

import main.service.crud.course.course.CourseCrudService;

import main.util.currentUser.CurrentUserService;
import main.util.labels.LabelProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;
import main.util.currentlanguagename.CurrentLanguageNameProvider;
import main.util.currentlanguagename.CurrentLanguageNameProviderImpl;

import main.json.response.AbstractResponseJson;
import main.json.response.CourseInfoResponseJson;
import main.json.response.CourseListResponseJson;
import main.json.response.AvailableLngAndTypesResponseJson;

import main.json.course.CourseInfoTeacherJson;
import main.json.course.CourseInfoStudentJson;
import main.json.course.NextLessonJson;
import main.json.course.HomeworkJson;
import main.json.course.TestJson;
import main.json.course.MessageJson;
import main.json.course.CourseUserJson;
import main.json.course.CourseListJson;
import main.json.course.AvailableLngAndTypesJson;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.CourseDay;
import main.model.course.Homework;
import main.model.course.Test;
import main.model.course.Message;
import main.model.course.CourseMembership;
import main.model.course.CourseType;
import main.model.language.Language;

import main.service.crud.language.LanguageCrudService;
import main.service.crud.course.coursetype.CourseTypeCrudService;

import main.security.coursemembership.annotations.CourseMembershipRequired;

@RequestMapping(value = CourseControllerUrlConstants.CLASS_URL)
@RestController
public class CourseController {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private CourseMembershipValidator courseMembershipValidator;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private CurrentLanguageNameProvider currentLanguageNameProvider;

    @Autowired
    private LanguageCrudService languageCrudService;

    @Autowired
    private CourseTypeCrudService courseTypeCrudService;

    @PostConstruct
    public void initialize() {
        this.currentLanguageNameProvider = new CurrentLanguageNameProviderImpl(this.localeResolver, this.httpServletRequest);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseInfo(@PathVariable("id") String courseID) {

        // Przy implementacji panelu administracyjnego dodać obsługę nadawania komunikatów o płatnościach dla użytkownika

		User currentUser = this.currentUserService.getCurrentUser();
        Assert.notNull(currentUser);
        Course course = this.courseCrudService.findCourseByID(courseID);
        Assert.notNull(course);

        if( this.courseMembershipValidator.isStudent(currentUser, course) ) {
            return getCourseInfoStudent(course, currentUser);
        }
        else if( this.courseMembershipValidator.isTeacher(currentUser, course) ) {
            return getCourseInfoTeacher(course, currentUser);
        }
        else {
            return new ResponseEntity<>(null); // this situation is impossible to appear
            // throw ArgumentException
        }
    }

    private ResponseEntity<? extends AbstractResponseJson> getCourseInfoTeacher(Course course, User user) {
        // Według Janka to może być w serwisie ;)

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("courseinfo.success");
        String languageName = this.currentLanguageNameProvider.getLanguageName(course.getLanguage());
        String courseTypeName = course.getCourseType().getCourseTypeName(this.localeResolver.resolveLocale(this.httpServletRequest).getLanguage());
        CourseInfoTeacherJson courseInfo = new CourseInfoTeacherJson(course.getId(), languageName, course.getCourseLevel().getName(), course.getCourseType().getId(), courseTypeName, getNextLesson(course));
        for( User teacher : course.getTeachers() ) {
            courseInfo.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        Set<Homework> incomingHomeworks = getIncomingHomeworks(course.getHomeworks(), user);
        Set<Test> incomingTests = getIncomingTests(course.getTests(), user);
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
        return new ResponseEntity<CourseInfoResponseJson>(new CourseInfoResponseJson(courseInfo, messageStr, responseStatus), responseStatus);
    }

    private ResponseEntity<? extends AbstractResponseJson> getCourseInfoStudent(Course course, User user) {
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("courseinfo.success");
        String languageName = this.currentLanguageNameProvider.getLanguageName(course.getLanguage());
        String courseTypeName = course.getCourseType().getCourseTypeName(this.localeResolver.resolveLocale(this.httpServletRequest).getLanguage());
        CourseInfoStudentJson courseInfo = new CourseInfoStudentJson(course.getId(), languageName, course.getCourseLevel().getName(), course.getCourseType().getId(), courseTypeName, getNextLesson(course));
        for( User teacher : course.getTeachers() ) {
            courseInfo.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        Set<Homework> incomingHomeworks = getIncomingHomeworks(course.getHomeworks(), user);
        Set<Test> incomingTests = getIncomingTests(course.getTests(), user);
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
        return new ResponseEntity<CourseInfoResponseJson>(new CourseInfoResponseJson(courseInfo, messageStr, responseStatus), responseStatus);
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

    private Set<Test> getIncomingTests(Set<Test> allTests, User user) {
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

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_STUDENT_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseStudentList(@PathVariable("id") String courseID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        Assert.notNull(course);
        String languageName = this.currentLanguageNameProvider.getLanguageName(course.getLanguage());
        String courseTypeName = course.getCourseType().getCourseTypeName(this.localeResolver.resolveLocale(this.httpServletRequest).getLanguage());
        CourseListJson result = new CourseListJson(course.getId(), languageName, course.getCourseLevel().getName(), course.getCourseType().getId(), courseTypeName);
        for( CourseMembership studentMembership : course.getStudents() ) {
            User student = studentMembership.getUser();
            result.addStudent(new CourseUserJson(student.getId(), student.getFullName()));
        }
        for( User teacher : course.getTeachers() ) {
            result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("coursestudentlist.success");
        return new ResponseEntity<CourseListResponseJson>(new CourseListResponseJson(result, messageStr, responseStatus), responseStatus);
    }

    @PermitAll
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_SHOW_AVAILABLE_LANGUAGES_AND_COURSE_TYPES, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> showAvailableLanguagesAndCourseTypes() {
        AvailableLngAndTypesJson result = new AvailableLngAndTypesJson();
        Set<Language> availableLanguages = this.languageCrudService.findAllLanguages();
        Set<CourseType> availableCourseTypes = this.courseTypeCrudService.findAllCourseTypes();
        for( Language language : availableLanguages ) {
            result.addLanguage(language.getId(), this.currentLanguageNameProvider.getLanguageName(language));
        }
        for( CourseType courseType : availableCourseTypes ) {
            result.addType(courseType.getId(), courseType.getCourseTypeName(this.localeResolver.resolveLocale(this.httpServletRequest).getLanguage()));
        }
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("course.showavailablelanguagesandcoursetypes");
        return new ResponseEntity<AvailableLngAndTypesResponseJson>(new AvailableLngAndTypesResponseJson(result, messageStr, responseStatus), responseStatus);
    }

    /*
    @PermitAll
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_SEARCH_COURSES, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> searchCourses(@RequestBody SearchPatternJson searchPattern) {
        // toDo
    }
    */
}
