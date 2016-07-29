package main.model.course;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="courseMemberships")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "courseMembershipID")) })
public class CourseMembership extends AbstractUuidModel {

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseID", referencedColumnName="courseID", nullable=false)
	private Course course;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userID", referencedColumnName="userID", nullable=false)
	private User user;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="student")
	private Set<StudentGrade> grades;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="courseIDMovedFrom", referencedColumnName="courseID", nullable=true)
	private Course movedFrom;

	@Getter
	@Setter
	@Type(type="org.hibernate.type.NumericBooleanType")
	@Column(name="active", nullable=false)
	private boolean active;

	@Getter
	@Setter
	@Type(type="org.hibernate.type.NumericBooleanType")
	@Column(name="resignation", nullable=false)
	private boolean resignation;

	public CourseMembership() {
		super();
	}

}