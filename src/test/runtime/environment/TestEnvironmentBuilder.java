package test.runtime.environment;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import main.model.UuidGenerator;
import main.model.course.*;
import main.model.language.*;
import main.model.placementtest.*;
import main.model.user.User;
import main.model.enums.GradeScale;
import main.model.enums.PhoneType;
import main.model.user.userprofile.*;
import main.model.user.userrole.UserRole;

public class TestEnvironmentBuilder {

	public static TestEnvironment build() {
		return build(true, null, null);
	}

	public static TestEnvironment build(boolean hasUUID, UserRole userUserRole, UserRole adminUserRole) {
		TestEnvironment environment = new TestEnvironment();

		UserRole user;
		if( userUserRole == null ) user = generateUserRole(hasUUID, "USER"); else user = userUserRole;
		UserRole admin;
		if( adminUserRole == null ) admin = generateUserRole(hasUUID, "ADMIN"); else admin = adminUserRole;

		environment.addUserRole(user);
		environment.addUserRole(admin);

		Language english = generateLanguage("EN", "English");
		environment.addLanguage(english);
		Language german = generateLanguage("DE", "German", english);
		environment.addLanguage(german);
		Language french = generateLanguage("FR", "French", english);
		environment.addLanguage(french);
		Language russian = generateLanguage("RU", "Russian", english);
		environment.addLanguage(russian);
		Language spanish = generateLanguage("ES", "Spanish", english);
		environment.addLanguage(spanish);
		Language portuguese = generateLanguage("PT", "Portuguese", english);
		environment.addLanguage(portuguese);
		Language italian = generateLanguage("IT", "Italian", english);
		environment.addLanguage(italian);

		CourseLevel a1 = generateCourseLevel("A1");
		CourseLevel a2 = generateCourseLevel("A2");
		CourseLevel b1 = generateCourseLevel("B1");
		CourseLevel b2 = generateCourseLevel("B2");
		CourseLevel c1 = generateCourseLevel("C1");
		CourseLevel c2 = generateCourseLevel("C2");

		environment.addCourseLevel(a1);
		environment.addCourseLevel(a2);
		environment.addCourseLevel(b1);
		environment.addCourseLevel(b2);
		environment.addCourseLevel(c1);
		environment.addCourseLevel(c2);

		CourseType standardCourseType = generateCourseType(hasUUID, "standard", english);
		CourseType businessCourseType = generateCourseType(hasUUID, "business", english);
		CourseType examCourseType = generateCourseType(hasUUID, "exam", english);

		environment.addCourseType(standardCourseType);
		environment.addCourseType(businessCourseType);
		environment.addCourseType(examCourseType);

		PlacementTest englishPlacementTest = generatePlacementTest(hasUUID, english);
		environment.addPlacementTest(englishPlacementTest);

		User sampleStudent1 = generateUser(hasUUID, "ramsay1", "ramsay1", "ramsay1@samplemail.com", "Ramsay", "Bolton", "Sample street 1", "12a", "5", "12-511", "Vdfs", user, generatePhone(hasUUID, PhoneType.MOBILE, "666-666-666"));
		environment.addUser(sampleStudent1);

		User sampleStudent2 = generateUser(hasUUID, "abc", "abc", "abc@samplemail.com", "A", "BC", "Sample street 2", "80", "5", "80-520", "Vdfs", admin, generatePhone(hasUUID, PhoneType.MOBILE, "552-222-222"));
		environment.addUser(sampleStudent2);

		User sampleTeacher1 = generateUser(hasUUID, "teacher1", "teacher1", "teacher1@samplemail.com", "Teacher", "Teacher", "Sample street 2", "80", "5", "80-520", "Vdfs", user, generatePhone(hasUUID, PhoneType.MOBILE, "625-856-926"));
		sampleTeacher1.addTaughtLanguage(english);
		environment.addUser(sampleTeacher1);

		User sampleTeacher2 = generateUser(hasUUID, "teacher2", "teacher2", "teacher2@samplemail.com", "Teacher2", "Teacher2", "Sample street 3", "80", "5", "80-520", "Vdfs", user, generatePhone(hasUUID, PhoneType.MOBILE, "625-856-926"));
		sampleTeacher2.addTaughtLanguage(english);
		sampleStudent2.addTaughtLanguage(german);
		environment.addUser(sampleTeacher2);

		PlacementTestResult englishUser1 = generatePlacementTestResult(hasUUID, englishPlacementTest, sampleStudent1, 80);
		PlacementTestResult englishUser2 = generatePlacementTestResult(hasUUID, englishPlacementTest, sampleStudent2, 65.5);

		environment.addPlacementTestResult(englishUser1);
		environment.addPlacementTestResult(englishUser2);

		File sampleFile1 = generateFile(hasUUID, "sample name 1", new Date(), "/dev/null", sampleStudent2);
		File sampleFile2 = generateFile(hasUUID, "sample name 2", new Date(), "/dev/null", sampleStudent1);
		File sampleFile3 = generateFile(hasUUID, "sample name 3", new Date(), "/dev/null", sampleTeacher1);

		environment.addFile(sampleFile1);
		environment.addFile(sampleFile2);

		Set<User> sampleEnglishCourse1Students = new HashSet<>();
		sampleEnglishCourse1Students.add(sampleStudent1);
		sampleEnglishCourse1Students.add(sampleStudent2);
		Course sampleEnglishCourse1 = generateCourse(hasUUID, english, a1, standardCourseType, new CourseActivity(new Date(2016,10,1), new Date(2017,6,30)), 14, 989.99, generateCourseDay(hasUUID, 5, 17,30, 21,30), sampleTeacher1, sampleEnglishCourse1Students, sampleFile3);

		environment.addCourse(sampleEnglishCourse1);

		for( CourseMembership courseMembership : sampleEnglishCourse1.getStudents() ) {
			if( hasUUID ) courseMembership.setId(UuidGenerator.newUUID());
			environment.addCourseMembership(courseMembership);
		}

		Course sampleEnglishCourse2 = generateCourse(hasUUID, english, a1, standardCourseType, new CourseActivity(new Date(2016,11,2), new Date(2017,7,31)), 14, 989.99, generateCourseDay(hasUUID, 5, 7,30, 11,30), sampleTeacher1, new HashSet<User>(), null);

		environment.addCourse(sampleEnglishCourse2);

		Message sampleCourseParticipantsMessage = generateMessage(hasUUID, sampleTeacher1, "sample course message 1", "sample content", sampleFile2, true, sampleEnglishCourse1);
		Message sampleSingleMessage1 = generateMessage(hasUUID, sampleStudent1, sampleStudent2, "sample single message 1", "sample content", sampleFile1, false, sampleEnglishCourse1);
		Message sampleSingleMessage2 = generateMessage(hasUUID, sampleStudent2, sampleStudent1, "sample single message 2", "sample content", sampleFile1, false, sampleEnglishCourse1);

		environment.addMessage(sampleCourseParticipantsMessage);
		environment.addMessage(sampleSingleMessage1);
		environment.addMessage(sampleSingleMessage2);

		Homework sampleHomework = generateHomework(hasUUID, "sample homework1", new Date(2016,9,19), "sample description", sampleEnglishCourse1, sampleFile2, sampleFile1, sampleStudent2);
		environment.addHomework(sampleHomework);

		Test sampleTest = generateTest(hasUUID, "sample test1", new Date(2016,9,19), "sample description", sampleEnglishCourse1, sampleStudent2, true);
		environment.addTest(sampleTest);

		Grade sampleGradeNoTask = generateGrade(hasUUID, sampleTeacher1, sampleEnglishCourse1, "sample grade title", "sample grade description", null, GradeScale.PUNKTOWA, 30, 1, sampleStudent2, 15);
		Grade sampleGradeToSampleHomework = generateGrade(hasUUID, sampleTeacher1, sampleEnglishCourse1, "sample grade title", "sample grade description", sampleHomework, GradeScale.PUNKTOWA, 30, 1, sampleStudent2, 18);
		Grade sampleGradeToSampleTest = generateGrade(hasUUID, sampleTeacher1, sampleEnglishCourse1, "sample grade title", "sample grade description", sampleTest, GradeScale.PUNKTOWA, 80, 1, sampleStudent2, 40);

		environment.addGrade(sampleGradeNoTask);
		environment.addGrade(sampleGradeToSampleHomework);
		environment.addGrade(sampleGradeToSampleTest);

		return environment;
	}

	private static UserRole generateUserRole(boolean hasUUID, String roleName) {
		UserRole role = new UserRole();
		role.setRoleName(roleName);
		if( hasUUID ) role.setId(UuidGenerator.newUUID());
		return role;
	}

	private static Language generateLanguage(String languageCode, String languageNameStr) {
		Language language = new Language(languageCode);
		LanguageName languageNameObj = new LanguageName(language, languageNameStr);
		languageNameObj.setId(languageCode, languageCode);
		return language;
	}

	private static Language generateLanguage(String languageCode, String languageName, Language namingLanguage) {
		Language language = new Language(languageCode);
		LanguageName languageNameObj = new LanguageName(language, namingLanguage, languageName);
		languageNameObj.setId(namingLanguage.getId(), languageCode);
		return language;
	}

	private static CourseLevel generateCourseLevel(String courseLevelName) {
		CourseLevel level = new CourseLevel(courseLevelName);
		return level;
	}


	private static CourseType generateCourseType(boolean hasUUID, String courseTypeName, Language naminglanguage) {
		CourseType type = new CourseType(courseTypeName, naminglanguage);
		if( hasUUID ) type.setId(UuidGenerator.newUUID());
		for( CourseTypeName name : type.getCourseTypeNames() ) {
			if( hasUUID ) name.setId(UuidGenerator.newUUID());
		}
		return type;
	}

	private static CourseDay generateCourseDay(boolean hasUUID, int day, int hourFrom, int minuteFrom, int hourTo, int minuteTo) {
		CourseDay result = new CourseDay(day, hourFrom,minuteFrom, hourTo,minuteTo);
		if( hasUUID ) result.setId(UuidGenerator.newUUID());
		return result;
	}

	private static PlacementTest generatePlacementTest(boolean hasUUID, Language language) {
		PlacementTest placementTest = new PlacementTest(language, generatePlacementTask(hasUUID));
		if( hasUUID ) placementTest.setId(UuidGenerator.newUUID());
		return placementTest;
	}

	private static Set<PlacementTask> generatePlacementTask(boolean hasUUID) {
		PlacementTask task = new PlacementTask("Sample command", generatePlacementSentence(hasUUID));
		if( hasUUID ) task.setId(UuidGenerator.newUUID());
		Set<PlacementTask> tasks = new HashSet<>();
		tasks.add(task);
		return tasks;
	}

	private static Set<PlacementSentence> generatePlacementSentence(boolean hasUUID) {
		PlacementSentence sentence = new PlacementSentence("sample prefix", "sample suffix", generatePlacementAnswers(hasUUID), "d");
		if( hasUUID ) sentence.setId(UuidGenerator.newUUID());
		Set<PlacementSentence> sentences = new HashSet<>();
		sentences.add(sentence);
		return sentences;
	}
	private static Set<PlacementAnswer> generatePlacementAnswers(boolean hasUUID) {
		PlacementAnswer a = new PlacementAnswer("a", "Sample answer a");
		PlacementAnswer b = new PlacementAnswer("b", "Sample answer b");
		PlacementAnswer c = new PlacementAnswer("c", "Sample answer c");
		PlacementAnswer d = new PlacementAnswer("d", "Sample answer d");
		if( hasUUID ) a.setId(UuidGenerator.newUUID());
		if( hasUUID ) b.setId(UuidGenerator.newUUID());
		if( hasUUID ) c.setId(UuidGenerator.newUUID());
		if( hasUUID ) d.setId(UuidGenerator.newUUID());
		Set<PlacementAnswer> answers = new HashSet<>();
		answers.add(a);
		answers.add(b);
		answers.add(c);
		answers.add(d);
		return answers;
	}

	private static User generateUser(boolean hasUUID, String username, String rawPassword, String email, String firstName, String lastName, String street, String houseNumber, String flatNumber, String postCode, String city, UserRole userRole, Phone phone) {
		User user = new User(username, new BCryptPasswordEncoder().encode(rawPassword), email, firstName, lastName, new Address( street, houseNumber, flatNumber, postCode, city ), userRole);
		user.addPhone(phone);
		if( hasUUID ) user.setId(UuidGenerator.newUUID());
		return user;
	}

	private static Phone generatePhone(boolean hasUUID, PhoneType phoneType, String phoneNumber) {
		Phone phone = new Phone(phoneType, phoneNumber);
		if( hasUUID ) phone.setId(UuidGenerator.newUUID());
		return phone;
	}

	private static PlacementTestResult generatePlacementTestResult(boolean hasUUID, PlacementTest test, User user, double result) {
		PlacementTestResult newPlacementTestResult = new PlacementTestResult(test, user, result);
		if( hasUUID ) newPlacementTestResult.setId(UuidGenerator.newUUID());
		return newPlacementTestResult;
	}

	private static File generateFile(boolean hasUUID, String name, Date date, String path, User sender) {
		File file = new File(name, date, path, sender);
		if( hasUUID ) file.setId(UuidGenerator.newUUID());
		return file;
	}

	private static Course generateCourse(boolean hasUUID, Language language, CourseLevel courseLevel, CourseType courseType, CourseActivity courseActivity, Integer maxStudents, Double price, CourseDay courseDay, User teacher, Set<User> students/*, Test test, Homework homework, Message message*/, File attachement) {
		Course course = new Course(language, courseLevel, courseType, courseActivity, maxStudents, price);
		course.addCourseDay(courseDay);
		course.addTeacher(teacher);
		for( User student : students ) {
			course.addStudent(new CourseMembership(student, course));
		}
		if( attachement != null ) course.addAttachement(attachement);
		if( hasUUID ) course.setId(UuidGenerator.newUUID());
		return course;
	}

	private static Message generateMessage(boolean hasUUID, User sender, Set<User> receivers, String title, String content, File attachement, boolean isAnnouncement, Course course) {
		Set<File> attachements = new HashSet<>();
		attachements.add(attachement);
		Message message = new Message(sender, receivers, title, content, attachements, isAnnouncement, course);
		if( hasUUID ) message.setId(UuidGenerator.newUUID());
		return message;
	}

	private static Message generateMessage(boolean hasUUID, User sender, User receiver, String title, String content, File attachement, boolean isAnnouncement, Course course) {
		Set<User> receivers = new HashSet<>();
		receivers.add(receiver);
		return generateMessage(hasUUID, sender, receivers, title, content, attachement, isAnnouncement, course);
	}

	private static Message generateMessage(boolean hasUUID, User sender, String title, String content, File attachement, boolean isAnnouncement, Course course) {
		Set<User> receivers = new HashSet<>();
		for( CourseMembership courseMembership : course.getStudents() ) {
			receivers.add(courseMembership.getUser());
		}
		return generateMessage(hasUUID, sender, receivers, title, content, attachement, isAnnouncement, course);
	}

	private static Homework generateHomework(boolean hasUUID, String title, Date date, String description, Course course, File attachement, File solutionFile, User user ) {
		Homework homework = new Homework(title, date, description, course);
		if( hasUUID ) homework.setId(UuidGenerator.newUUID());
		homework.addAttachement(attachement);
		// bez grade
		HomeworkSolution homeworkSolution = new HomeworkSolution(getCourseMembership(course, user), homework, solutionFile);
		homework.addHomeworkSolution(homeworkSolution);
		return homework;
	}

	private static CourseMembership getCourseMembership(Course course, User user) {
		for( CourseMembership courseMembership : course.getStudents() ) {
			if( courseMembership.getUser().equals(user) ) {
				return courseMembership;
			}
		}
		return null;
	}

	private static Test generateTest(boolean hasUUID, String title, Date date, String description, Course course, User user, boolean written ) {
		Test test = new Test(title, date, description, course);
		if( hasUUID ) test.setId(UuidGenerator.newUUID());
		// bez grade
		TestSolution testSolution = new TestSolution(getCourseMembership(course, user), test, written);
		if( hasUUID ) testSolution.setId(UuidGenerator.newUUID());
		test.addTestSolution(testSolution);
		return test;
	}
	private static Grade generateGrade(boolean hasUUID, User gradedBy, Course course, String gradeTitle, String gradeDescription, AbstractHomeworkOrTest task, GradeScale scale, double maxPoints, double weight, User student, double gradeValue) {
		Grade grade = new Grade(gradedBy, course, gradeTitle, gradeDescription, task, scale, maxPoints, weight);
		if( hasUUID ) grade.setId(UuidGenerator.newUUID());
		StudentGrade studentGrade = new StudentGrade(getCourseMembership(course, student), gradeValue, grade);
		if( hasUUID ) studentGrade.setId(UuidGenerator.newUUID());
		return grade;
	}
}
