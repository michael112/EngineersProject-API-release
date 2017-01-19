package main.model.course;

import java.util.Set;
import java.util.HashSet;

import org.joda.time.LocalDate;

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
		if( language != null ) {
			if (this.language != null) {
				if (this.language.containsCourse(this)) {
					this.language.changeCourseLanguage(this, language);
				}
			}
			this.language = language;
			language.addCourse(this); // przypisanie powiązania
		}
		else throw new IllegalArgumentException();
	}

	@Getter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseLevelName", referencedColumnName="name", nullable=false)
	private CourseLevel courseLevel;
	public void setCourseLevel(CourseLevel courseLevel) {
		if( courseLevel != null ) {
			if (this.courseLevel != null) {
				if (this.courseLevel.containsCourse(this)) {
					this.courseLevel.changeCourse(this, courseLevel);
				}
			}
			this.courseLevel = courseLevel;
			courseLevel.addCourse(this); // przypisanie powiązania
		}
		else throw new IllegalArgumentException();
	}

	@Getter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseTypeID", referencedColumnName="courseTypeID", nullable=false)
	private CourseType courseType;
	public void setCourseType(CourseType courseType) {
		if (courseType != null) {
			if (this.courseType != null) {
				if (this.courseType.containsCourse(this)) {
					this.courseType.changeCourseCourseType(this, courseType);
				}
			}
			this.courseType = courseType;
			courseType.addCourse(this); // przypisanie powiązania
		}
		else throw new IllegalArgumentException();
	}

	@Getter
	@Setter
	@Embedded
	private CourseActivity courseActivity;

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, orphanRemoval=true)
	@JoinColumn(name="courseID", referencedColumnName="courseID", nullable=false)
	private Set<CourseDay> courseDays;
	public void setCourseDays(Set<CourseDay> courseDays) {
		this.courseDays.clear();
		if( courseDays != null ) {
			this.courseDays.addAll(courseDays);
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
	public boolean containsCourseDay(CourseDay courseDay) {
		return this.courseDays.contains(courseDay);
	}
	public CourseDay getCourseDay(String courseDayID) {
		for( CourseDay courseDay : this.getCourseDays() ) {
			if( courseDay.getId().equals(courseDayID) ) {
				return courseDay;
			}
		}
		return null;
	}

	@Getter
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="coursesAsTeacher")
	private Set<User> teachers;
	public void setTeachers(Set<User> teachers) {
		this.teachers.clear();
		if( teachers != null ) {
			this.teachers.addAll(teachers);
			for( User teacher : teachers ) {
				if( !( teacher.containsCourseAsTeacher(this) ) ) {
					teacher.addCourseAsTeacher(this); // przypisanie powiązania
				}
			}
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
		this.students.clear();
		if( students != null ) {
			this.students.addAll(students);
			for( CourseMembership student : students ) {
				if( ( student.getCourse() == null ) || ( !( student.getCourse().equals(this) ) ) ) {
					student.setCourse(this); // przypisanie powiązania
				}
			}
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
	public CourseMembership getCourseMembership(User student) {
		for( CourseMembership cm : this.getStudents() ) {
			if( cm.getUser().getId().equals(student.getId()) ) {
				return cm;
			}
		}
		return null;
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="course", orphanRemoval=true)
	private Set<Test> tests;
	public void setTests(Set<Test> tests) {
		this.tests.clear();
		if( tests != null ) {
			this.tests.addAll(tests);
			for( Test test : tests ) {
				if( ( test.getCourse() == null ) || ( !( test.getCourse().equals(this) ) ) ) {
					test.setCourse(this); // przypisanie powiązania
				}
			}
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
		this.tests.remove(test);
	}
	public boolean containsTest(Test test) {
		return this.tests.contains(test);
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="course", orphanRemoval=true)
	private Set<Homework> homeworks;
	public void setHomeworks(Set<Homework> homeworks) {
		this.homeworks.clear();
		if( homeworks != null ) {
			this.homeworks.addAll(homeworks);
			for( Homework homework : homeworks ) {
				if( ( homework.getCourse() == null ) || ( !( homework.getCourse().equals(this) ) ) ) {
					homework.setCourse(this); // przypisanie powiązania
				}
			}
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
		this.homeworks.remove(homework);
	}
	public boolean containsHomework(Homework homework) {
		return this.homeworks.contains(homework);
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="course", orphanRemoval=true)
	private Set<Grade> grades;
	public void setGrades(Set<Grade> grades) {
		this.grades.clear();
		if( grades != null ) {
			this.grades.addAll(grades);
			for( Grade grade : grades ) {
				if( ( grade.getCourse() != null ) || ( !( grade.getCourse().equals(this) ) ) ) {
					grade.setCourse(this); // przypisanie powiązania
				}
			}
		}
	}
	public void addGrade(Grade grade) {
		if ( !( this.grades.contains(grade) ) ) {
			this.grades.add(grade);
		}
		if( grade.getCourse() != this ) {
			grade.setCourse(this); // przypisanie powiązania
		}
	}
	public boolean containsGrade(Grade grade) {
		return this.grades.contains(grade);
	}
	public void changeGradeCourse(Grade grade, Course newCourse) {
		this.grades.remove(grade);
		grade.setCourse(newCourse);
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="course", orphanRemoval=true)
	private Set<Message> messages;
	public void setMessages(Set<Message> messages) {
		this.messages.clear();
		if( messages != null ) {
			this.messages.addAll(messages);
			for( Message message : messages ) {
				if( ( message.getCourse() == null ) || ( !( message.getCourse().equals(this) ) ) ) {
					message.setCourse(this); // przypisanie powiązania
				}
			}
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
		this.messages.remove(message);
	}
	public boolean containsMessage(Message message) {
		return this.messages.contains(message);
	}

	@Getter
	@ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinTable(name = "attachementscourses",
			joinColumns = { @JoinColumn(name = "courseID", referencedColumnName="courseID") },
			inverseJoinColumns = { @JoinColumn(name = "attachementID", referencedColumnName="fileID") })
	private Set<File> attachements;
	public void setAttachements(Set<File> attachements) {
		this.attachements.clear();
		if( attachements != null ) {
			this.attachements.addAll(attachements);
		}
	}
	public void addAttachement(File attachement) {
		if ( !( this.attachements.contains(attachement) ) ) {
			this.attachements.add(attachement);
		}
	}
	public void removeAttachement(File attachement) {
		this.attachements.remove(attachement);
	}
	public boolean containsAttachement(File attachement) {
		return this.attachements.contains(attachement);
	}

	@Getter
	@Setter
	@Column(name="maxStudents", nullable=true)
	private Integer maxStudents;

	@Getter
	@Setter
	@Column(name="price", nullable=true)
	private Double price;

	public Course() {
		super();
		this.courseDays = new HashSet<>();
		this.teachers = new HashSet<>();
		this.students = new HashSet<>();
		this.tests = new HashSet<>();
		this.homeworks = new HashSet<>();
		this.grades = new HashSet<>();
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

	public Course(Language language, CourseLevel courseLevel, CourseType courseType, CourseActivity courseActivity, Integer maxStudents, Double price) {
		this(language, courseLevel, courseType, courseActivity);
		this.setMaxStudents(maxStudents);
		this.setPrice(price);
	}

	public boolean isActive() {
		return this.getCourseActivity().getTo().isBefore(new LocalDate());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object otherObj) {
		return super.equals(otherObj);
	}

}