package main.json.course;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public class CourseInfoStudentJson extends AbstractCourseInfoJson {

    @Getter
    private Set<PaymentMessageJson> paymentMessages;

    @Getter
    private String courseID;

    @Getter
    private String language;

	@Getter
	private String courseLevel;

    @Getter
    private CourseTypeJson courseType;

    @Getter
    private Set<CourseUserJson> teachers;

    @Getter
    private Set<TestJson> incomingTests;

    @Getter
    private Set<HomeworkJson> incomingHomeworks;

    @Getter
    private Set<MessageJson> teacherMessages;

    @Getter
    private NextLessonJson nextLesson;

    private CourseInfoStudentJson(String courseID, String language, String courseLevel) {
        this.courseID = courseID;
        this.language = language;
		this.courseLevel = courseLevel;
        this.paymentMessages = new HashSet<>();
        this.teachers = new HashSet<>();
        this.incomingTests = new HashSet<>();
        this.incomingHomeworks = new HashSet<>();
        this.teacherMessages = new HashSet<>();
        this.nextLesson = null;
    }

    public CourseInfoStudentJson(String courseID, String language, String courseLevel, CourseTypeJson courseType) {
        this(courseID, language, courseLevel);
        this.courseType = courseType;
    }

    public CourseInfoStudentJson(String courseID, String language, String courseLevel, String courseTypeID, String courseTypeName) {
        this(courseID, language, courseLevel);
        this.courseType = new CourseTypeJson(courseTypeID, courseTypeName);
    }

    public CourseInfoStudentJson(String courseID, String language, String courseLevel, CourseTypeJson courseType, NextLessonJson nextLesson) {
        this(courseID, language, courseLevel, courseType);
        this.nextLesson = nextLesson;
    }

    public CourseInfoStudentJson(String courseID, String language, String courseLevel, String courseTypeID, String courseTypeName, NextLessonJson nextLesson) {
        this(courseID, language, courseLevel, courseTypeID, courseTypeName);
        this.nextLesson = nextLesson;
    }

    public CourseInfoStudentJson(String courseID, String language, String courseLevel, CourseTypeJson courseType, String nextLessonDay, String nextLessonHour) {
        this(courseID, language, courseLevel, courseType);
        this.nextLesson = new NextLessonJson(nextLessonDay, nextLessonHour);
    }

    public CourseInfoStudentJson(String courseID, String language, String courseLevel, String courseTypeID, String courseTypeName, String nextLessonDay, String nextLessonHour) {
        this(courseID, language, courseLevel, courseTypeID, courseTypeName);
        this.nextLesson = new NextLessonJson(nextLessonDay, nextLessonHour);
    }

    public void addPaymentMessage(PaymentMessageJson message) {
        this.paymentMessages.add(message);
    }

    public void addTeacher(CourseUserJson teacher) {
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
