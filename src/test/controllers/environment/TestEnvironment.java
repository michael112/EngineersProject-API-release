package test.controllers.environment;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;

import main.model.course.*;
import main.model.language.Language;
import main.model.placementtest.PlacementTest;
import main.model.user.User;
import main.model.user.userprofile.PlacementTestResult;
import main.model.user.userrole.UserRole;

public class TestEnvironment {

	@Getter
	private List<UserRole> userRoles;

	@Getter
	private List<Language> languages;

	@Getter
	private List<CourseLevel> courseLevels;

	@Getter	
	private List<CourseType> courseTypes;

	@Getter
	private List<PlacementTest> placementTests;

	@Getter
	private List<User> users; // depends on: PlacementTestResult, CourseMembership (=> Course), Message, Course(as teacher), Language

	@Getter
	private List<PlacementTestResult> placementTestResults; // depends on User

	@Getter
	private List<File> files; // depends on User

	@Getter
	private List<Course> courses; // depends on User (teachers) and CourseMembership (students), Test, Homework, Message, File, 

	@Getter
	private List<Message> messages; // depends on User, Course

	@Getter
	private List<Homework> homeworks; // depends on Grade, Course, File [solution: CourseMembership - User + Course; ]

	@Getter
	private List<Test> tests; // depends on Grade, Course [solution: CourseMembership - User + Course; ]

	@Getter
	private List<Grade> grades; // depends on User, Course, [potentially: Homework / Test]

	@Getter
	private List<CourseMembership> courseMemberships; // depends on User, Course, Grade - add grade

	public void addUserRole(UserRole userRole) {
		this.userRoles.add(userRole);
	}
	public void addLanguage(Language language) {
		this.languages.add(language);
	}
	public void addCourseLevel(CourseLevel courseLevel) {
		this.courseLevels.add(courseLevel);
	}
	public void addCourseType(CourseType courseType) {
		this.courseTypes.add(courseType);
	}
	public void addFile(File file) {
		this.files.add(file);
	}
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	public void addPlacementTest(PlacementTest placementTest) {
		this.placementTests.add(placementTest);
	}
	public void addPlacementTestResult(PlacementTestResult placementTestResult) {
		this.placementTestResults.add(placementTestResult);
	}
	public void addCourse(Course course) {
		this.courses.add(course);
	}
	public void addHomework(Homework homework) {
		this.homeworks.add(homework);
	}
	public void addTest(Test test) {
		this.tests.add(test);
	}
	public void addGrade(Grade grade) {
		this.grades.add(grade);
	}
	public void addUser(User user) {
		this.users.add(user);
	}
	public void addCourseMembership(CourseMembership courseMembership) {
		this.courseMemberships.add(courseMembership);
	}

	public TestEnvironment() {
		this.userRoles = new ArrayList<>();
		this.languages = new ArrayList<>();
		this.courseLevels = new ArrayList<>();
		this.courseTypes = new ArrayList<>();
		this.files = new ArrayList<>();
		this.messages = new ArrayList<>();
		this.placementTests = new ArrayList<>();
		this.placementTestResults = new ArrayList<>();
		this.courses = new ArrayList<>();
		this.homeworks = new ArrayList<>();
		this.tests = new ArrayList<>();
		this.grades = new ArrayList<>();
		this.users = new ArrayList<>();
		this.courseMemberships = new ArrayList<>();
	}

}
