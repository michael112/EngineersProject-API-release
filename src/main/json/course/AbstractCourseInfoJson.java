package main.json.course;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class AbstractCourseInfoJson extends CourseJson {

    @Getter
    protected Set<TestJson> incomingTests;

    @Getter
    protected Set<HomeworkJson> incomingHomeworks;

    @Getter
    protected Set<MessageJson> teacherMessages;

    @Getter
    protected NextLessonJson nextLesson;

    public AbstractCourseInfoJson(String courseID, String languageID, String languageName, String courseLevelID, String courseLevelName, String courseTypeID, String courseTypeName) {
        super(courseID, languageID, languageName, courseLevelID, courseLevelName, courseTypeID, courseTypeName);
        this.incomingTests = new HashSet<>();
        this.incomingHomeworks = new HashSet<>();
        this.teacherMessages = new HashSet<>();
        this.nextLesson = null;
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
