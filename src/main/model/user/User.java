// (C) websystique

package main.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

import main.model.abstracts.AbstractUuidModel;

import main.model.user.userrole.UserRole;
import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;
import main.model.user.userprofile.PlacementTestResult;
import main.model.course.Course;
import main.model.course.CourseMembership;
import main.model.course.Message;
import main.model.language.Language;

@Entity
@Table(name="users")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "userID")) })
public class User extends AbstractUuidModel {

    @Column(name="username", unique=true, nullable=false)
    @Getter
    @Setter
    private String username;

    @Column(name="password", nullable=false)
    @Getter
    @Setter
    private String password;

    @Column(name="email", nullable=false)
    @Getter
    @Setter
    private String email;

    @Column(name="active", nullable=false)
    @Type(type="org.hibernate.type.NumericBooleanType")
    @Getter
    @Setter
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usersuserroles",
            joinColumns = { @JoinColumn(name = "userID", referencedColumnName="userID") },
            inverseJoinColumns = { @JoinColumn(name = "userRoleID", referencedColumnName="roleID") })
    @Getter
    @Setter
    private Set<UserRole> userRoles = new HashSet<UserRole>();

    @Column(name="firstname", nullable=false)
    @Getter
    @Setter
    private String firstName;

    @Column(name="lastname", nullable=false)
    @Getter
    @Setter
    private String lastName;
	
	@Getter
	@Setter
    @OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
    @JoinColumn(name="userID", referencedColumnName="userID")
	private Set<Phone> phone;

	@Getter
	@Setter
    @Embedded
	private Address address;
	
	@Getter
	@Setter
    /*
    @OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
    @JoinColumn(name="userID", referencedColumnName="userID")
    */
    @OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="user")
	private Set<PlacementTestResult> placementTest;

	@Getter
	@Setter
    /*
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="userID", referencedColumnName="userID")
    */
    @OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="user")
	private Set<CourseMembership> coursesAsStudent;

    @Getter
    @Setter
    @OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="sender")
    private Set<Message> myMessages;

	@Getter
	@Setter
    /*
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "messagesusers",
            joinColumns = { @JoinColumn(name = "userID", referencedColumnName="userID") },
            inverseJoinColumns = { @JoinColumn(name = "messageID", referencedColumnName="messageID") })
    */
    @ManyToMany(fetch=FetchType.LAZY, mappedBy="receivers")
	private Set<Message> messages; // wiadomości, których user jest jednym z adresatów

	@Getter
	@Setter
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "teacherslanguages",
            joinColumns = { @JoinColumn(name = "teacherID", referencedColumnName="userID") },
            inverseJoinColumns = { @JoinColumn(name = "languageID", referencedColumnName="languageID") })
	private Set<Language> taughtLanguages;

	@Getter
	@Setter
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "teacherscourses",
            joinColumns = { @JoinColumn(name = "teacherID", referencedColumnName="userID") },
            inverseJoinColumns = { @JoinColumn(name = "courseID", referencedColumnName="courseID") })
	private Set<Course> coursesAsTeacher;

    public User() {
        super();
    }

}
