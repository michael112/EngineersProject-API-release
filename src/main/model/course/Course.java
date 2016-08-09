package main.model.course;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;
import lombok.Setter;

import main.model.language.Language;
import main.model.user.User;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="courses")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "courseID")) })
public class Course extends AbstractUuidModel {

	@Getter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="languageID", referencedColumnName="languageID", nullable=false)
	private Language language;
	public void setLanguage(Language language) {
		// do sprawdzenia
		if( this.language != null ) {
			if (this.language.containsCourse(this)) {
				this.language.changeCourseLanguage(this, language);
			}
		}
		this.language = language;
		language.addCourse(this); // przypisanie powiązania
	}

	@Getter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseLevelName", referencedColumnName="name", nullable=false)
	private CourseLevel courseLevel;
	public void setCourseLevel(CourseLevel courseLevel) {
		// do sprawdzenia
		if( this.courseLevel != null ) {
			if (this.courseLevel.containsCourse(this)) {
				this.courseLevel.changeCourse(this, courseLevel);
			}
		}
		this.courseLevel = courseLevel;
		courseLevel.addCourse(this); // przypisanie powiązania
	}

	@Getter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseTypeID", referencedColumnName="courseTypeID", nullable=false)
	private CourseType courseType;
	public void setCourseType(CourseType courseType) {
		// do sprawdzenia
		if( this.courseType != null ) {
			if (this.courseType.containsCourse(this)) {
				this.courseType.changeCourseCourseType(this, courseType);
			}
		}
		this.courseType = courseType;
		courseType.addCourse(this); // przypisanie powiązania
	}

	@Getter
	@Setter
	@Embedded
	private CourseActivity courseActivity;

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="courseID", referencedColumnName="courseID", nullable=false)
	private Set<CourseDay> courseDays;
	public void setCourseDays(Set<CourseDay> courseDays) {
		if( courseDays != null ) {
			this.courseDays = courseDays;
		}
		else {
			this.courseDays = new HashSet<>();
		}
	}
	public void addCourseDay(CourseDay courseDay) {
		if( !( this.courseDays.contains(courseDay) ) ) {
			this.courseDays.add(courseDay);
		}
	}
	public void removeCourseDay(CourseDay courseDay) {
		this.courseDays.remove(courseDay);
	}

	@Getter
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="coursesAsTeacher")
	private Set<User> teachers;
	public void setTeachers(Set<User> teachers) {
		if( teachers != null ) {
			this.teachers = teachers;
		}
		else {
			this.teachers = new HashSet<>();
		}
	}
	public void addTeacher(User teacher) {
		if ( !( this.teachers.contains(teacher) ) ) {
			this.teachers.add(teacher);
		}
		if( !( teacher.getCoursesAsTeacher().contains(this) ) ) {
			teacher.addCourseAsTeacher(this); // przypisanie powiązania
		}
	}
	public void removeTeacher(User teacher) {
		this.teachers.remove(teacher);
		if( teacher.containsCourseAsTeacher(this) ) {
			teacher.removeCourseAsTeacher(this);
		}
	}
	public boolean containsTeacher(User teacher) {
		return this.teachers.contains(teacher);
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, mappedBy="course")
	private Set<CourseMembership> students;
	public void setStudents(Set<CourseMembership> students) {
		if( students != null ) {
			this.students = students;
		}
		else {
			this.students = new HashSet<>();
		}
	}
	public void addStudent(CourseMembership student) {
		if ( !( this.students.contains(student) ) ) {
			this.students.add(student);
		}
		if( student.getCourse() != this ) {
			student.setCourse(this); // przypisanie powiązania
		}
	}
	public boolean containsStudent(CourseMembership student) {
		return this.students.contains(student);
	}
	public void changeStudentCourse(CourseMembership student, Course newCourse) {
		this.students.remove(student);
		student.setCourse(newCourse);
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="course")
	private Set<Test> tests;
	public void setTests(Set<Test> tests) {
		if( tests != null ) {
			this.tests = tests;
		}
		else {
			this.tests = new HashSet<>();
		}
	}
	public void addTest(Test test) {
		if ( !( this.tests.contains(test) ) ) {
			this.tests.add(test);
		}
		if( test.getCourse() != this ) {
			test.setCourse(this); // przypisanie powiązania
		}
	}
	public void removeTest(Test test) {
		this.tests.remove(test); // powinno powodować usunięcie testu z bazy (sprawdzić!)
	}
	public boolean containsTest(Test test) {
		return this.tests.contains(test);
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="course")
	private Set<Homework> homeworks;
	public void setHomeworks(Set<Homework> homeworks) {
		if( homeworks != null ) {
			this.homeworks = homeworks;
		}
		else {
			this.homeworks = new HashSet<>();
		}
	}
	public void addHomework(Homework homework) {
		if ( !( this.homeworks.contains(homework) ) ) {
			this.homeworks.add(homework);
		}
		if( homework.getCourse() != this ) {
			homework.setCourse(this); // przypisanie powiązania
		}
	}
	public void removeHomework(Homework homework) {
		this.homeworks.remove(homework); // powinno powodować usunięcie homework'a z bazy (sprawdzić!)
	}
	public boolean containsHomework(Homework homework) {
		return this.homeworks.contains(homework);
	}

	@Getter
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="course")
	private Set<Message> messages;
	public void setMessages(Set<Message> messages) {
		if( messages != null ) {
			this.messages = messages;
		}
		else {
			this.messages = new HashSet<>();
		}
	}
	public void addMessage(Message message) {
		if ( !( this.messages.contains(message) ) ) {
			this.messages.add(message);
		}
		if( message.getCourse() != this ) {
			message.setCourse(this); // przypisanie powiązania
		}
	}
	public void removeMessage(Message message) {
		this.messages.remove(message); // powinno powodować usunięcie wiadomości z bazy (sprawdzić!)
	}
	public boolean containsMessage(Message message) {
		return this.messages.contains(message);
	}

	@Getter
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinTable(name = "attachementscourses",
			joinColumns = { @JoinColumn(name = "courseID", referencedColumnName="courseID") },
			inverseJoinColumns = { @JoinColumn(name = "attachementID", referencedColumnName="fileID") })
	private Set<File> attachements;
	public void setAttachements(Set<File> attachements) {
		if( attachements != null ) {
			this.attachements = attachements;
		}
		else {
			this.attachements = new HashSet<>();
		}
	}
	public void addAttachement(File attachement) {
		if ( !( this.attachements.contains(attachement) ) ) {
			this.attachements.add(attachement);
		}
	}
	public void removeAttachement(File attachement) {
		this.attachements.remove(attachement); // powinno powodować usunięcie pliku z bazy (sprawdzić!)
	}
	public boolean containsAttachement(File attachement) {
		return this.attachements.contains(attachement);
	}

	@Getter
	@Setter
	@Column(name="maxStudents", nullable=true)
	private int maxStudents;

	@Getter
	@Setter
	@Column(name="price", nullable=true)
	private double price;

	public Course() {
		super();
		this.courseDays = new HashSet<>();
		this.teachers = new HashSet<>();
		this.students = new HashSet<>();
		this.tests = new HashSet<>();
		this.homeworks = new HashSet<>();
		this.messages = new HashSet<>();
		this.attachements = new HashSet<>();
	}

	public Course(Language language, CourseLevel courseLevel, CourseType courseType) {
		this();
		this.setLanguage(language);
		this.setCourseLevel(courseLevel);
		this.setCourseType(courseType);
	}

	public Course(Language language, CourseLevel courseLevel, CourseType courseType, CourseActivity courseActivity) {
		this(language, courseLevel, courseType);
		this.setCourseActivity(courseActivity);
	}
	
}