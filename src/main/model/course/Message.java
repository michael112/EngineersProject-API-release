package main.model.course;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
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
@Table(name="messages")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "messageID")) })
public class Message extends AbstractUuidModel {

	@Getter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="senderID", referencedColumnName="userID", nullable=false)
	private User sender;
	public void setSender(User sender) {
		if( sender != null ) {
			if( this.sender != null ) {
				if (this.sender.containsMyMessage(this)) {
					this.sender.removeMyMessage(this);
				}
			}
			this.sender = sender;
			sender.addMyMessage(this); // przypisanie powiązania
		}
		else throw new IllegalArgumentException();
	}

	@Getter
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "messagesusers",
			joinColumns = { @JoinColumn(name = "messageID", referencedColumnName="messageID") },
			inverseJoinColumns = { @JoinColumn(name = "userID", referencedColumnName="userID") })
	private Set<User> receivers; // osoba lub osoby będące adresatami wiadomości - groupID trzeba zewnętrznie przetworzyć na listę osób
	public void setReceivers(Set<User> receivers) {
		this.receivers.clear();
		if( receivers != null ) {
			this.receivers.addAll(receivers);
			for( User receiver : receivers ) {
				if( !( receiver.containsMessage(this)) ) {
					receiver.addMessage(this); // przypisanie powiązania
				}
			}
		}
	}
	public void addReceiver(User receiver) {
		if ( !( this.receivers.contains(receiver) ) ) {
			this.receivers.add(receiver);
		}
		if( !( receiver.containsMessage(this) ) ) {
			receiver.addMessage(this); // przypisanie powiązania
		}
	}
	public void removeReceiver(User receiver) {
		this.receivers.remove(receiver);
		if( receiver.containsMessage(this) ) {
			receiver.removeMessage(this);
		}
	}
	public boolean containsReceiver(User receiver) {
		return this.receivers.contains(receiver);
	}

	@Getter
	@Setter
	@Column(name="title", nullable=false)
	private String title;

	@Getter
	@Setter
	@Column(name="content", nullable=false)
	private String content;

	@Getter
	@ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinTable(name = "attachementsmessages",
			joinColumns = { @JoinColumn(name = "messageID", referencedColumnName="messageID") },
			inverseJoinColumns = { @JoinColumn(name = "fileID", referencedColumnName="fileID") })
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
	@Column(name="isAnnouncement", nullable=false)
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean isAnnouncement;

	@Getter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseID", referencedColumnName="courseID", nullable=true)
	private Course course;
	public void setCourse(Course course) {
		if( this.course != null ) {
			if (this.course.containsMessage(this)) {
				this.course.removeMessage(this);
			}
		}
		this.course = course;
		if( course != null ) course.addMessage(this); // przypisanie powiązania
	}

	public Message() {
		super();
		this.receivers = new HashSet<>();
		this.attachements = new HashSet<>();
		this.setAnnouncement(false);
	}

	public Message(User sender, String title, String content) {
		this();
		this.setSender(sender);
		this.setTitle(title);
		this.setContent(content);
	}

	public Message(User sender, String title, String content, boolean isAnnouncement) {
		this();
		this.setSender(sender);
		this.setTitle(title);
		this.setContent(content);
		this.setAnnouncement(isAnnouncement);
	}

	public Message(User sender, User receiver, String title, String content, boolean isAnnouncement) {
		this();
		this.setSender(sender);
		this.addReceiver(receiver);
		this.setTitle(title);
		this.setContent(content);
		this.setAnnouncement(isAnnouncement);
	}

	public Message(User sender, Set<User> receivers, String title, String content, boolean isAnnouncement, Course course) {
		this(sender, title, content, isAnnouncement);
		this.setReceivers(receivers);
		this.setCourse(course);
	}

	public Message(User sender, Set<User> receivers, String title, String content, Set<File> attachements, boolean isAnnouncement, Course course) {
		this(sender, receivers, title, content, isAnnouncement, course);
		this.setAttachements(attachements);
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