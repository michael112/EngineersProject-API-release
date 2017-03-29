package main.service.controller.course;

import java.util.Set;
import java.util.HashSet;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.joda.time.LocalDate;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

import main.service.controller.AbstractService;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.coursetype.CourseTypeCrudService;
import main.service.crud.course.coursemembership.CourseMembershipCrudService;
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
import main.json.course.CourseJson;
import main.json.course.CourseSignupJson;
import main.json.course.CourseListJson;
import main.json.course.CourseUserJson;
import main.json.course.CourseDayJson;
import main.json.course.CourseInfoTeacherJson;
import main.json.course.CourseInfoStudentJson;
import main.json.course.NextLessonJson;
import main.json.course.HomeworkJson;
import main.json.course.TestJson;
import main.json.course.MessageJson;
import main.json.course.changegroup.ChangeGroupFormJson;
import main.json.course.changegroup.DayOfCourseJson;
import main.json.course.changegroup.SimilarGroupJson;

@Service("courseService")
public class CourseServiceImpl extends AbstractService implements CourseService {

    private CourseCrudService courseCrudService;

    private CourseTypeCrudService courseTypeCrudService;

    private CourseMembershipCrudService courseMembershipCrudService;

    private LanguageCrudService languageCrudService;

    private DateTimeFormatter dateFormatter;

    public CourseListJson getCourseStudentList(Course course) {
        try {
            String languageName = course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode());
            String courseTypeName = course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode());
            CourseListJson result = new CourseListJson(course.getId(), course.getLanguage().getId(), languageName, course.getCourseLevel().getId(), course.getCourseLevel().getName(), course.getCourseType().getId(), courseTypeName);
            for( CourseMembership studentMembership : course.getStudents() ) {
                User student = studentMembership.getUser();
                result.addStudent(new CourseUserJson(student.getId(), student.getFullName()));
            }
            for( User teacher : course.getTeachers() ) {
                result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public CourseInfoStudentJson getCourseInfoStudent(Course course, User user) {
        try {
            String languageName = course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode());
            String courseTypeName = course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode());
            CourseInfoStudentJson courseInfo = new CourseInfoStudentJson(course.getId(), course.getLanguage().getId(), languageName, course.getCourseLevel().getId(), course.getCourseLevel().getName(), course.getCourseType().getId(), courseTypeName, getNextLesson(course));
            for( User teacher : course.getTeachers() ) {
                courseInfo.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
            }
            Set<Homework> incomingHomeworks = getIncomingHomeworks(course.getHomeworks(), user);
            Set<Test> incomingTests = getIncomingTests(course.getTests());
            Set<Message> teacherMessages = getTeacherMessages(course, user);
            for( Homework homework : incomingHomeworks ) {
                courseInfo.addIncomingHomework(new HomeworkJson(homework.getId(), this.dateFormatter.print(homework.getDate()), homework.getTitle()));
            }
            for( Test test : incomingTests ) {
                courseInfo.addIncomingTest(new TestJson(test.getId(), this.dateFormatter.print(test.getDate()), test.getTitle()));
            }
            for( Message message : teacherMessages ) {
                courseInfo.addTeacherMessage(new MessageJson(message.getId(), message.getTitle()));
            }
            return courseInfo;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public CourseInfoTeacherJson getCourseInfoTeacher(Course course, User user) {
        try {
            String languageName = course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode());
            String courseTypeName = course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode());
            CourseInfoTeacherJson courseInfo = new CourseInfoTeacherJson(course.getId(), course.getLanguage().getId(), languageName, course.getCourseLevel().getId(), course.getCourseLevel().getName(), course.getCourseType().getId(), courseTypeName, getNextLesson(course));
            for( User teacher : course.getTeachers() ) {
                courseInfo.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
            }
            Set<Test> incomingTests = getIncomingTests(course.getTests());
            Set<Message> teacherMessages = getTeacherMessages(course, user);
            for( Test test : incomingTests ) {
                courseInfo.addIncomingTest(new TestJson(test.getId(), this.dateFormatter.print(test.getDate()), test.getTitle()));
            }
            for( Message message : teacherMessages ) {
                courseInfo.addTeacherMessage(new MessageJson(message.getId(), message.getTitle()));
            }
            return courseInfo;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
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

    public CourseSignupJson getSignupCourseInfo(Course course) {
        try {
            CourseSignupJson result = new CourseSignupJson(course.getId(), course.getLanguage().getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), course.getCourseLevel().getId(), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()), course.getPrice());
            for( User teacher : course.getTeachers() ) {
                result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
            }
            for( CourseDay cd : course.getCourseDays() ) {
                result.addCourseDay( new CourseDayJson(cd.getDay().getDay(), cd.getHourFrom().getHour(), cd.getHourFrom().getMinute()) );
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void confirmSignupToCourse(User user, Course course) {
        try {
            CourseMembership cm = new CourseMembership(user, course, false);
            this.courseMembershipCrudService.saveCourseMembership(cm);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public ChangeGroupFormJson getChangeGroupForm(Course course) {
        try {
            ChangeGroupFormJson result = new ChangeGroupFormJson(course.getLanguage().getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), course.getCourseLevel().getId(), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
            // przetestować
            Set<Course> similarCourses = this.courseCrudService.findCoursesByQuery("select course from Course course join course.language language join course.courseLevel courseLevel join course.courseType courseType where ( language.id = '" + course.getLanguage().getId() + "' ) and ( courseLevel.name = '" + course.getCourseLevel().getName() + "' ) and ( courseType.id = '" + course.getCourseType().getId() + "' ) and ( course.id != '" + course.getId() + "' )");
            for( Course similarCourse : similarCourses ) {
                SimilarGroupJson similarGroupJson = new SimilarGroupJson(similarCourse.getId(), similarCourse.getLanguage().getId(), similarCourse.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), similarCourse.getCourseLevel().getId(), similarCourse.getCourseLevel().getName(), similarCourse.getCourseType().getId(), similarCourse.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()), course.getStudents().size(), course.getPrice());
                for( CourseDay courseDay : similarCourse.getCourseDays() ) {
                    similarGroupJson.addDayOfCourse(new DayOfCourseJson(courseDay.getDay().getDayName(), courseDay.getHourFrom().getTime()));
                }
                for( User teacher : course.getTeachers() ) {
                    similarGroupJson.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
                }
                result.addSimilarGroup(similarGroupJson);
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void changeGroup(User user, Course oldCourse, Course newCourse) {
        try {
            CourseMembership cm = oldCourse.getCourseMembership(user);
            cm.setCourse(newCourse);
            cm.setMovedFrom(oldCourse);
            cm.setActive(false);
            this.courseMembershipCrudService.updateCourseMembership(cm);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public CourseJson getResignGroupForm(Course course) {
        CourseJson result = new CourseJson(course.getId(), course.getLanguage().getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), course.getCourseLevel().getId(), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
        for( User teacher : course.getTeachers() ) {
            result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        return result;
    }

    public void resignGroup(User user, Course course) {
        CourseMembership cm = course.getCourseMembership(user);
        cm.setResignation(true);
        this.courseMembershipCrudService.updateCourseMembership(cm);
    }

    private NextLessonJson getNextLesson(Course course) {
        LocalDate iteratorDate = new LocalDate();

        LocalDate resultDate = null;
        String resultHour = null;

        for(CourseDay courseDay : course.getCourseDays()) {
            while( iteratorDate.getDayOfWeek() != courseDay.getDay().getDay() ) {
                iteratorDate = iteratorDate.plusDays(1);
            }
            if( ( resultDate == null ) || ( iteratorDate.isBefore(resultDate) ) ) {
                resultDate = iteratorDate;
                resultHour = courseDay.getHourFrom().getTime();
            }
        }
        try {
            String resultDateStr = resultDate == null ? null : this.dateFormatter.print(resultDate);

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

        for( Test test : allTests ) {
            if( new LocalDate().isBefore(test.getDate()) ) {
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
    public CourseServiceImpl(LocaleCodeProvider localeCodeProvider, CourseCrudService courseCrudService, CourseTypeCrudService courseTypeCrudService, CourseMembershipCrudService courseMembershipCrudService, LanguageCrudService languageCrudService) {
        super(localeCodeProvider);
        this.courseCrudService = courseCrudService;
        this.courseTypeCrudService = courseTypeCrudService;
        this.courseMembershipCrudService = courseMembershipCrudService;
        this.languageCrudService = languageCrudService;
        this.dateFormatter = DateTimeFormat.forPattern("dd-MM-yyyy");;
    }
}
