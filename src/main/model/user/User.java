// Based on: http://websystique.com/spring-security/spring-security-4-hibernate-annotation-example/

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
@Access(AccessType.FIELD)
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usersuserroles",
            joinColumns = { @JoinColumn(name = "userID", referencedColumnName="userID") },
            inverseJoinColumns = { @JoinColumn(name = "userRoleID", referencedColumnName="roleID") })
    @Getter
    private Set<UserRole> userRoles;
    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles.clear();
        if( userRoles != null ) {
            this.userRoles.addAll(userRoles);
        }
    }
    public void addUserRole(UserRole userRole) {
        if ( !( this.userRoles.contains(userRole) ) ) {
            this.userRoles.add(userRole);
        }
    }
    public void removeUserRole(UserRole userRole) {
        this.userRoles.remove(userRole);
    }
    public boolean containsUserRole(UserRole userRole) {
        return this.userRoles.contains(userRole);
    }

    @Column(name="firstname", nullable=false)
    @Getter
    @Setter
    private String firstName;

    @Column(name="lastname", nullable=false)
    @Getter
    @Setter
    private String lastName;
	
	@Getter
    @OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, orphanRemoval=true)
    @JoinColumn(name="userID", referencedColumnName="userID", nullable=false)
	private Set<Phone> phone;
    public void setPhone(Set<Phone> phone) {
        this.phone.clear();
        if( phone != null ) {
            this.phone.addAll(phone);
        }
    }
    public void addPhone(Phone phone) {
        if ( !( this.phone.contains(phone) ) ) {
            this.phone.add(phone);
        }
    }
    public void removePhone(Phone phone) {
        this.phone.remove(phone);
    }
    public boolean containsPhone(Phone phone) {
        return this.phone.contains(phone);
    }
    public Phone getPhoneById(String phoneID) {
        for( Phone phone : this.phone ) {
            if( phone.getId().equals(phoneID) ) {
                return phone;
            }
        }
        return null;
    }
    public Phone getPhoneByNumber(String phoneNumber) {
        for( Phone phone : this.phone ) {
            if( phone.getPhoneNumber().equals(phoneNumber) ) {
                return phone;
            }
        }
        return null;
    }

	@Getter
	@Setter
    @Embedded
	private Address address;
	
    @Getter
    @OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="user", orphanRemoval=true)
	private Set<PlacementTestResult> placementTest;
    public void setPlacementTest(Set<PlacementTestResult> placementTest) {
        this.placementTest.clear();
        if( placementTest != null ) {
            this.placementTest.addAll(placementTest);
            for( PlacementTestResult placementTestResult : placementTest ) {
                if( ( placementTestResult.getUser() == null ) || ( !( placementTestResult.getUser().equals(this) )) ) {
                    placementTestResult.setUser(this); // przypisanie powi??zania
                }
            }
        }
    }
    public void addPlacementTest(PlacementTestResult placementTest) {
        if ( !( this.placementTest.contains(placementTest) ) ) {
            this.placementTest.add(placementTest);
        }
        if( placementTest.getUser() != this ) {
            placementTest.setUser(this); // przypisanie powi??zania
        }
    }
    public void removePlacementTest(PlacementTestResult placementTest) {
        this.placementTest.remove(placementTest);
    }
    public boolean containsPlacementTest(PlacementTestResult placementTest) {
        return this.placementTest.contains(placementTest);
    }

    @Getter
    @OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="user")
	private Set<CourseMembership> coursesAsStudent;
    public void setCoursesAsStudent(Set<CourseMembership> coursesAsStudent) {
        this.coursesAsStudent.clear();
        if( coursesAsStudent != null ) {
            this.coursesAsStudent.addAll(coursesAsStudent);
            for( CourseMembership courseAsStudent : coursesAsStudent ) {
                if( ( courseAsStudent.getUser() == null ) || ( !( courseAsStudent.getUser().equals(this) ) ) ) {
                    courseAsStudent.setUser(this); // przypisanie powi??zania
                }
            }
        }
    }
    public void addCourseAsStudent(CourseMembership courseAsStudent) {
        if ( !( this.coursesAsStudent.contains(courseAsStudent) ) ) {
            this.coursesAsStudent.add(courseAsStudent);
        }
        if( courseAsStudent.getUser() != this ) {
            courseAsStudent.setUser(this); // przypisanie powi??zania
        }
    }
    public void removeCourseAsStudent(CourseMembership courseAsStudent) {
        this.coursesAsStudent.remove(courseAsStudent); // powinno powodowa?? usuni??cie z bazy (sprawdzi??!)
    }
    public boolean containsCourseAsStudent(CourseMembership courseAsStudent) {
        return this.coursesAsStudent.contains(courseAsStudent);
    }

    @Getter
    @OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="sender", orphanRemoval=true)
    private Set<Message> myMessages;
    public void setMyMessages(Set<Message> myMessages) {
        this.myMessages.clear();
        if( myMessages != null ) {
            this.myMessages.addAll(myMessages);
            for( Message message : myMessages ) {
                if( ( message.getSender() == null )  || ( !( message.getSender().equals(this) ) ) ) {
                    message.setSender(this); // przypisanie powi??zania
                }
            }
        }
    }
    public void addMyMessage(Message myMessage) {
        if ( !( this.myMessages.contains(myMessage) ) ) {
            this.myMessages.add(myMessage);
        }
        if( myMessage.getSender() != this ) {
            myMessage.setSender(this); // przypisanie powi??zania
        }
    }
    public void removeMyMessage(Message myMessage) {
        this.myMessages.remove(myMessage);
    }
    public boolean containsMyMessage(Message myMessage) {
        return this.myMessages.contains(myMessage);
    }

    @Getter
    @ManyToMany(fetch=FetchType.LAZY, mappedBy="receivers")
	private Set<Message> messages; // wiadomo??ci, kt??rych user jest jednym z adresat??w
    public void setMessages(Set<Message> messages) {
        this.messages.clear();
        if( messages != null ) {
            this.messages.addAll(messages);
            for( Message message : messages ) {
                if( !( message.containsReceiver(this) ) ) {
                    message.addReceiver(this); // przypisanie powi??zania
                }
            }
        }
    }
    public void addMessage(Message message) {
        if ( !( this.messages.contains(message) ) ) {
            this.messages.add(message);
        }
        if( !( message.containsReceiver(this) ) ) {
            message.addReceiver(this); // przypisanie powi??zania
        }
    }
    public void removeMessage(Message message) {
        this.messages.remove(message);
        if( message.containsReceiver(this) ) {
            message.removeReceiver(this);
        }
    }
    public boolean containsMessage(Message message) {
        return this.messages.contains(message);
    }

	@Getter
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "teacherslanguages",
            joinColumns = { @JoinColumn(name = "teacherID", referencedColumnName="userID") },
            inverseJoinColumns = { @JoinColumn(name = "languageID", referencedColumnName="languageID") })
	private Set<Language> taughtLanguages;
    public void setTaughtLanguages(Set<Language> taughtLanguages) {
        this.taughtLanguages.clear();
        if( taughtLanguages != null ) {
            this.taughtLanguages.addAll(taughtLanguages);
            for( Language language : taughtLanguages ) {
                if( !( language.containsTeacher(this) ) ) {
                    language.addTeacher(this); // przypisanie powi??zania
                }
            }
        }
    }
    public void addTaughtLanguage(Language taughtLanguage) {
        if ( !( this.taughtLanguages.contains(taughtLanguage) ) ) {
            this.taughtLanguages.add(taughtLanguage);
        }
        if( !( taughtLanguage.containsTeacher(this) ) ) {
            taughtLanguage.addTeacher(this); // przypisanie powi??zania
        }
    }
    public void removeTaughtLanguage(Language taughtLanguage) {
        this.taughtLanguages.remove(taughtLanguage);
        if( taughtLanguage.containsTeacher(this) ) {
            taughtLanguage.removeTeacher(this);
        }
    }
    public boolean containsTaughtLanguage(Language taughtLanguage) {
        return this.taughtLanguages.contains(taughtLanguage);
    }

	@Getter
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "teacherscourses",
            joinColumns = { @JoinColumn(name = "teacherID", referencedColumnName="userID") },
            inverseJoinColumns = { @JoinColumn(name = "courseID", referencedColumnName="courseID") })
	private Set<Course> coursesAsTeacher;
    public void setCoursesAsTeacher(Set<Course> coursesAsTeacher) {
        this.coursesAsTeacher.clear();
        if( coursesAsTeacher != null ) {
            this.coursesAsTeacher.addAll(coursesAsTeacher);
            for( Course course : coursesAsTeacher ) {
                if( !( course.containsTeacher(this) ) ) {
                    course.addTeacher(this); // przypisanie powi??zania
                }
            }
        }
    }
    public void addCourseAsTeacher(Course course) {
        if ( !( this.coursesAsTeacher.contains(course) ) ) {
            this.coursesAsTeacher.add(course);
        }
        if( !( course.getTeachers().contains(this) ) ) {
            course.addTeacher(this);
        }
    }
    public boolean containsCourseAsTeacher(Course course) {
        return this.coursesAsTeacher.contains(course);
    }
    public void removeCourseAsTeacher(Course course) {
        this.coursesAsTeacher.remove(course);
        if( course.containsTeacher(this) ) {
            course.removeTeacher(this);
        }
    }

    public String getFullName() {
        return this.getFirstName() + ' ' + this.getLastName();
    }

    public boolean hasActiveCourses(Language language) {
        try {
            for( Course course : this.getCoursesAsTeacher() ) {
                if( ( course.getLanguage().equals(language) ) && ( course.isActive() ) ) return true;
            }
            return false;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }

    public User() {
        super();
        this.setActive(true);
        this.userRoles = new HashSet<>();
        this.phone = new HashSet<>();
        this.placementTest = new HashSet<>();
        this.coursesAsStudent = new HashSet<>();
        this.myMessages = new HashSet<>();
        this.messages = new HashSet<>();
        this.taughtLanguages = new HashSet<>();
        this.coursesAsTeacher = new HashSet<>();
    }

    public User(String username, String password, String email, String firstName, String lastName) {
        this();
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }
    public User(String username, String password, String email, String firstName, String lastName, Address address, UserRole userRole) {
        this(username, password, email, firstName, lastName);
        this.setAddress(address);
        this.addUserRole(userRole);
    }

    public User(String username, String password, String email, String firstName, String lastName, Set<Phone> phone, Address address, UserRole userRole) {
        this(username, password, email, firstName, lastName, address, userRole );
        this.setPhone(phone);
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
