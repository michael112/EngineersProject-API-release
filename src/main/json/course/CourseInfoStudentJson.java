package main.json.course;

import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public class CourseInfoStudentJson extends AbstractCourseInfoJson {

    @Getter
    private Set<PaymentMessageJson> paymentMessages;

    private CourseInfoStudentJson(String courseID, String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName) {
        super(courseID, languageID, languageName, courseLevelID, courseLevelName, courseTypeID, courseTypeName);
    }

    public CourseInfoStudentJson(String courseID, String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName, NextLessonJson nextLesson) {
        this(courseID, languageID, languageName, courseLevelID, courseLevelName, courseTypeID, courseTypeName);
        this.nextLesson = nextLesson;
    }

    public void addPaymentMessage(PaymentMessageJson message) {
        this.paymentMessages.add(message);
    }

    public void addTeacher(CourseUserJson teacher) {
        super.addTeacher(teacher);
    }

    public void addIncomingTest(TestJson test) {
        super.addIncomingTest(test);
    }

    public void addIncomingHomework(HomeworkJson homework) {
        super.addIncomingHomework(homework);
    }

    public void addTeacherMessage(MessageJson message) {
        super.addTeacherMessage(message);
    }
}
