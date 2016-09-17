package test.controllers.environment;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import main.model.UuidGenerator;
import main.model.course.*;
import main.model.language.*;
import main.model.placementtest.*;
import main.model.user.User;
import main.model.enums.PhoneType;
import main.model.user.userprofile.*;
import main.model.user.userrole.UserRole;

public class TestEnvironmentBuilder {

	public static TestEnvironment build() {
		TestEnvironment environment = new TestEnvironment();

		UserRole user = generateUserRole("USER");
		UserRole admin = generateUserRole("ADMIN");

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

		CourseType standardCourseType = generateCourseType("standard", english);
		CourseType businessCourseType = generateCourseType("business", english);
		CourseType examCourseType = generateCourseType("exam", english);

		environment.addCourseType(standardCourseType);
		environment.addCourseType(businessCourseType);
		environment.addCourseType(examCourseType);

		PlacementTest englishPlacementTest = generatePlacementTest(english);
		environment.addPlacementTest(englishPlacementTest);

		User sampleStudent1 = generateUser("ramsay1", "ramsay1", "ramsay1@samplemail.com", "Ramsay", "Bolton", "Sample street 1", "12a", "5", "12-511", "Vdfs", user, new Phone(PhoneType.MOBILE, "666-666-666"));
		environment.addUser(sampleStudent1);

		User sampleStudent2 = generateUser("abc", "abc", "abc@samplemail.com", "A", "BC", "Sample street 2", "80", "5", "80-520", "Vdfs", admin, new Phone(PhoneType.MOBILE, "552-222-222"));
		environment.addUser(sampleStudent2);

		User sampleTeacher1 = generateUser("teacher1", "teacher1", "teacher1@samplemail.com", "Teacher", "Teacher", "Sample street 2", "80", "5", "80-520", "Vdfs", user, new Phone(PhoneType.MOBILE, "625-856-926"));
		environment.addUser(sampleTeacher1);

		PlacementTestResult englishUser1 = generatePlacementTestResult(englishPlacementTest, sampleStudent1, 80);
		PlacementTestResult englishUser2 = generatePlacementTestResult(englishPlacementTest, sampleStudent2, 65.5);

		environment.addPlacementTestResult(englishUser1);
		environment.addPlacementTestResult(englishUser2);

		File sampleFile1 = generateFile("sample name 1", new Date(), "/dev/null", sampleStudent2);
		File sampleFile2 = generateFile("sample name 2", new Date(), "/dev/null", sampleStudent1);

		environment.addFile(sampleFile1);
		environment.addFile(sampleFile2);

		Set<User> sampleEnglishCourse1Students = new HashSet<>();
		sampleEnglishCourse1Students.add(sampleStudent1);
		sampleEnglishCourse1Students.add(sampleStudent2);
		Course sampleEnglishCourse1 = generateCourse(english, a1, standardCourseType, new CourseActivity(new Date(2016,10,1), new Date(2017,6,30)), 14, 989.99, new CourseDay(5, 17,30, 21,30), sampleTeacher1, sampleEnglishCourse1Students, sampleFile1);

		environment.addCourse(sampleEnglishCourse1);

		for( CourseMembership courseMembership : sampleEnglishCourse1.getStudents() ) {
			environment.addCourseMembership(courseMembership);
		}

		Message sampleCourseParticipantsMessage = generateMessage(sampleTeacher1, "sample course message 1", "sample content", sampleFile2, true, sampleEnglishCourse1);
		Message sampleSingleMessage1 = generateMessage(sampleStudent1, sampleStudent2, "sample single message 1", "sample content", sampleFile1, false, sampleEnglishCourse1);
		Message sampleSingleMessage2 = generateMessage(sampleStudent2, sampleStudent1, "sample single message 2", "sample content", sampleFile1, false, sampleEnglishCourse1);

		environment.addMessage(sampleCourseParticipantsMessage);
		environment.addMessage(sampleSingleMessage1);
		environment.addMessage(sampleSingleMessage2);



		return environment;
	}

	private static UserRole generateUserRole(String roleName) {
		UserRole role = new UserRole();
		role.setRoleName(roleName);
		role.setId(UuidGenerator.newUUID());
		return role;
	}

	private static Language generateLanguage(String languageCode, String languageNameStr) {
		Language language = new Language(languageCode);
		LanguageName languageNameObj = new LanguageName(language, languageNameStr);
		return language;
	}

	private static Language generateLanguage(String languageCode, String languageName, Language namingLanguage) {
		Language language = new Language(languageCode);
		LanguageName languageNameObj = new LanguageName(language, namingLanguage, languageName);
		return language;
	}

	private static CourseLevel generateCourseLevel(String courseLevelName) {
		CourseLevel level = new CourseLevel(courseLevelName);
		level.setId(UuidGenerator.newUUID());
		return level;
	}


	private static CourseType generateCourseType(String courseTypeName, Language naminglanguage) {
		CourseType type = new CourseType(courseTypeName, naminglanguage);
		type.setId(UuidGenerator.newUUID());
		return type;
	}

	private static PlacementTest generatePlacementTest(Language language) {
		PlacementTest placementTest = new PlacementTest(language, generatePlacementTask());
		placementTest.setId(UuidGenerator.newUUID());
		return placementTest;
	}

	private static Set<PlacementTask> generatePlacementTask() {
		PlacementTask task = new PlacementTask("Sample command", generatePlacementSentence());
		task.setId(UuidGenerator.newUUID());
		Set<PlacementTask> tasks = new HashSet<>();
		tasks.add(task);
		return tasks;
	}

	private static Set<PlacementSentence> generatePlacementSentence() {
		PlacementSentence sentence = new PlacementSentence("sample prefix", "sample suffix", generatePlacementAnswers(), "d");
		sentence.setId(UuidGenerator.newUUID());
		Set<PlacementSentence> sentences = new HashSet<>();
		sentences.add(sentence);
		return sentences;
	}
	private static Set<PlacementAnswer> generatePlacementAnswers() {
		PlacementAnswer a = new PlacementAnswer("a", "Sample answer a");
		PlacementAnswer b = new PlacementAnswer("b", "Sample answer b");
		PlacementAnswer c = new PlacementAnswer("c", "Sample answer c");
		PlacementAnswer d = new PlacementAnswer("d", "Sample answer d");
		a.setId(UuidGenerator.newUUID());
		b.setId(UuidGenerator.newUUID());
		c.setId(UuidGenerator.newUUID());
		d.setId(UuidGenerator.newUUID());
		Set<PlacementAnswer> answers = new HashSet<>();
		answers.add(a);
		answers.add(b);
		answers.add(c);
		answers.add(d);
		return answers;
	}

	private static User generateUser(String username, String rawPassword, String email, String firstName, String lastName, String street, String houseNumber, String flatNumber, String postCode, String city, UserRole userRole, Phone phone) {
		User user = new User(username, new BCryptPasswordEncoder().encode(rawPassword), email, firstName, lastName, new Address( street, houseNumber, flatNumber, postCode, city ), userRole);
		user.addPhone(phone);
		user.setId(UuidGenerator.newUUID());
		return user;
	}

	private static PlacementTestResult generatePlacementTestResult(PlacementTest test, User user, double result) {
		PlacementTestResult newPlacementTestResult = new PlacementTestResult(test, user, result);
		newPlacementTestResult.setId(UuidGenerator.newUUID());
		return newPlacementTestResult;
	}

	private static File generateFile(String name, Date date, String path, User sender) {
		File file = new File(name, date, path, sender);
		file.setId(UuidGenerator.newUUID());
		return file;
	}

	private static Course generateCourse(Language language, CourseLevel courseLevel, CourseType courseType, CourseActivity courseActivity, Integer maxStudents, Double price, CourseDay courseDay, User teacher, Set<User> students/*, Test test, Homework homework, Message message*/, File attachement) {
		Course course = new Course(language, courseLevel, courseType, courseActivity, maxStudents, price);
		course.addCourseDay(courseDay);
		course.addTeacher(teacher);
		for( User student : students ) {
			course.addStudent(new CourseMembership(student, course));
		}
		course.addAttachement(attachement);
		course.setId(UuidGenerator.newUUID());
		return course;
	}

	private static Message generateMessage(User sender, Set<User> receivers, String title, String content, File attachement, boolean isAnnouncement, Course course) {
		Set<File> attachements = new HashSet<>();
		attachements.add(attachement);
		Message message = new Message(sender, receivers, title, content, attachements, isAnnouncement, course);
		message.setId(UuidGenerator.newUUID());
		return message;
	}

	private static Message generateMessage(User sender, User receiver, String title, String content, File attachement, boolean isAnnouncement, Course course) {
		Set<User> receivers = new HashSet<>();
		receivers.add(receiver);
		return generateMessage(sender, receivers, title, content, attachement, isAnnouncement, course);
	}

	private static Message generateMessage(User sender, String title, String content, File attachement, boolean isAnnouncement, Course course) {
		Set<User> receivers = new HashSet<>();
		for( CourseMembership courseMembership : course.getStudents() ) {
			receivers.add(courseMembership.getUser());
		}
		return generateMessage(sender, receivers, title, content, attachement, isAnnouncement, course);
	}

	private static Homework generateHomework() {return null;}
	private static Test generateTest() {return null;}
	private static Grade generateGrade() {return null;}
}
