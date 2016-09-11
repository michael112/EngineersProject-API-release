package main.json.course;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

public class CourseInfoStudentJson {

    @Getter
    private Set<PaymentMessageJson> paymentMessages;

    @Getter
    private String courseID;

    @Getter
    private String language;

    @Getter
    private CourseTypeJson courseType;

    @Getter
    private Set<CourseTeacherJson> teachers;

    @Getter
    private Set<TestJson> incomingTests;

    @Getter
    private Set<HomeworkJson> incomingHomeworks;

    @Getter
    private Set<MessageJson> teacherMessages;

    @Getter
    private NextLessonJson nextLesson;

    private CourseInfoStudentJson(String courseID, String language) {
        this.courseID = courseID;
        this.language = language;
        this.paymentMessages = new HashSet<>();
        this.teachers = new HashSet<>();
        this.incomingTests = new HashSet<>();
        this.incomingHomeworks = new HashSet<>();
        this.teacherMessages = new HashSet<>();
        this.nextLesson = null;
    }

    public CourseInfoStudentJson(String courseID, String language, CourseTypeJson courseType) {
        this(courseID, language);
        this.courseType = courseType;
    }

    public CourseInfoStudentJson(String courseID, String language, String courseTypeID, String courseTypeName) {
        this(courseID, language);
        this.courseType = new CourseTypeJson(courseTypeID, courseTypeName);
    }

    public CourseInfoStudentJson(String courseID, String language, CourseTypeJson courseType, NextLessonJson nextLesson) {
        this(courseID, language, courseType);
        this.nextLesson = nextLesson;
    }

    public CourseInfoStudentJson(String courseID, String language, String courseTypeID, String courseTypeName, NextLessonJson nextLesson) {
        this(courseID, language, courseTypeID, courseTypeName);
        this.nextLesson = nextLesson;
    }

    public CourseInfoStudentJson(String courseID, String language, CourseTypeJson courseType, String nextLessonDay, String nextLessonHour) {
        this(courseID, language, courseType);
        this.nextLesson = new NextLessonJson(nextLessonDay, nextLessonHour);
    }

    public CourseInfoStudentJson(String courseID, String language, String courseTypeID, String courseTypeName, String nextLessonDay, String nextLessonHour) {
        this(courseID, language, courseTypeID, courseTypeName);
        this.nextLesson = new NextLessonJson(nextLessonDay, nextLessonHour);
    }

    public void addPaymentMessage(PaymentMessageJson message) {
        this.paymentMessages.add(message);
    }

    public void addTeacher(CourseTeacherJson teacher) {
        this.teachers.add(teacher);
    }

    public void addIncomingTest(TestJson test) {
        this.incomingTests.add(test);
    }

    public void addIncomingHomework(HomeworkJson homework) {
        this.incomingHomeworks.add(homework);
    }

    public void addTeacherMessage(MessageJson message) {
        this.teacherMessages.add(message);
    }
}
