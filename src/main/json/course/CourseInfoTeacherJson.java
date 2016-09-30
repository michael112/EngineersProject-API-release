package main.json.course;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseInfoTeacherJson extends AbstractCourseInfoJson {

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

    private CourseInfoTeacherJson(String courseID, String language, String courseLevel) {
        this.courseID = courseID;
        this.language = language;
		this.courseLevel = courseLevel;
        this.teachers = new HashSet<>();
        this.incomingTests = new HashSet<>();
        this.incomingHomeworks = new HashSet<>();
        this.teacherMessages = new HashSet<>();
        this.nextLesson = null;
    }

    public CourseInfoTeacherJson(String courseID, String language, String courseLevel, CourseTypeJson courseType) {
        this(courseID, language, courseLevel);
        this.courseType = courseType;
    }

    public CourseInfoTeacherJson(String courseID, String language, String courseLevel, String courseTypeID, String courseTypeName) {
        this(courseID, language, courseLevel);
        this.courseType = new CourseTypeJson(courseTypeID, courseTypeName);
    }

    public CourseInfoTeacherJson(String courseID, String language, String courseLevel, CourseTypeJson courseType, NextLessonJson nextLesson) {
        this(courseID, language, courseLevel, courseType);
        this.nextLesson = nextLesson;
    }

    public CourseInfoTeacherJson(String courseID, String language, String courseLevel, String courseTypeID, String courseTypeName, NextLessonJson nextLesson) {
        this(courseID, language, courseLevel, courseTypeID, courseTypeName);
        this.nextLesson = nextLesson;
    }

    public CourseInfoTeacherJson(String courseID, String language, String courseLevel, CourseTypeJson courseType, String nextLessonDay, String nextLessonHour) {
        this(courseID, language, courseLevel, courseType);
        this.nextLesson = new NextLessonJson(nextLessonDay, nextLessonHour);
    }

    public CourseInfoTeacherJson(String courseID, String language, String courseLevel, String courseTypeID, String courseTypeName, String nextLessonDay, String nextLessonHour) {
        this(courseID, language, courseLevel, courseTypeID, courseTypeName);
        this.nextLesson = new NextLessonJson(nextLessonDay, nextLessonHour);
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
