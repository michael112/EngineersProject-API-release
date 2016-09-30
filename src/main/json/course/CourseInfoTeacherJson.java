package main.json.course;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

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

    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            CourseInfoStudentJson other = (CourseInfoStudentJson) otherObj;
            if( !( this.getCourseID().equals(other.getCourseID()) ) ) return false;
            if( !( this.getLanguage().equals(other.getLanguage()) ) ) return false;
            if( !( this.getCourseLevel().equals(other.getCourseLevel()) ) ) return false;
            if( !( this.getCourseType().equals(other.getCourseType()) ) ) return false;
            if( this.getTeachers().size() != other.getTeachers().size() ) return false;
            java.util.List<CourseUserJson> thisTeachers = new java.util.ArrayList<>(this.getTeachers());
            java.util.List<CourseUserJson> otherTeachers = new java.util.ArrayList<>(other.getTeachers());
            for( int i = 0; i < this.getTeachers().size(); i++ ) {
                if( !( thisTeachers.get(i).equals(otherTeachers.get(i)) ) ) return false;
            }
            if( this.getIncomingTests().size() != other.getIncomingTests().size() ) return false;
            java.util.List<TestJson> thisIncomingTests = new java.util.ArrayList<>(this.getIncomingTests());
            java.util.List<TestJson> otherIncomingTests = new java.util.ArrayList<>(other.getIncomingTests());
            for( int i = 0; i < this.getIncomingTests().size(); i++ ) {
                if( !( thisIncomingTests.get(i).equals(otherIncomingTests.get(i)) ) ) return false;
            }
            if( this.getIncomingHomeworks().size() != other.getIncomingHomeworks().size() ) return false;
            java.util.List<HomeworkJson> thisIncomingHomeworks = new java.util.ArrayList<>(this.getIncomingHomeworks());
            java.util.List<HomeworkJson> otherIncomingHomeworks = new java.util.ArrayList<>(other.getIncomingHomeworks());
            for( int i = 0; i < this.getIncomingHomeworks().size(); i++ ) {
                if( !( thisIncomingHomeworks.get(i).equals(otherIncomingHomeworks.get(i)) ) ) return false;
            }
            if( this.getTeacherMessages().size() != other.getTeacherMessages().size() ) return false;
            java.util.List<MessageJson> thisTeacherMessages = new java.util.ArrayList<>(this.getTeacherMessages());
            java.util.List<MessageJson> otherTeacherMessages = new java.util.ArrayList<>(other.getTeacherMessages());
            for( int i = 0; i < this.getTeacherMessages().size(); i++ ) {
                if( !( thisTeacherMessages.get(i).equals(otherTeacherMessages.get(i)) ) ) return false;
            }
			if( !( this.getNextLesson().equals(other.getNextLesson() ) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
