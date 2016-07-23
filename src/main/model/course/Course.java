package main.model.course;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.language.Language;
import main.model.user.User;

import main.model.AbstractModel;

@Entity
@Table(name="courses")
public class Course extends AbstractModel<String> {
	
	// ===== fields =====
	
	@Getter
	@Setter
	@Id
	@Column(name="courseID")
	private String id;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="languageID", referencedColumnName="languageID", nullable=false)
	private Language language;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="name", referencedColumnName="courseLevelName", nullable=false)
	private CourseLevel courseLevel;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseTypeID", referencedColumnName="courseTypeID", nullable=false)
	private CourseType courseType;

	@Getter
	@Setter
	@Embedded
	private CourseActivity courseActivity;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="courseID", referencedColumnName="courseDayID")
	private Set<CourseDay> courseDays;

	@Getter
	@Setter
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="coursesAsTeacher")
	private Set<User> teachers;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.EAGER, mappedBy="course")
	private Set<CourseMembership> students;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="course")
	private Set<Test> tests;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="course")
	private Set<Homework> homeworks;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="course")
	private Set<Message> messages;

	@Getter
	@Setter
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinTable(name = "attachementscourses",
			joinColumns = { @JoinColumn(name = "courseID", referencedColumnName="courseID") },
			inverseJoinColumns = { @JoinColumn(name = "attachementID", referencedColumnName="fileID") })
	private Set<File> attachements;

	@Getter
	@Setter
	@Column(name="maxStudents", nullable=true)
	private int maxStudents;

	@Getter
	@Setter
	@Column(name="price", nullable=true)
	private double price;

	public Course() {
		this.id = new UUID().toString();
	}
	
}