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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="senderID", referencedColumnName="userID")
	private User sender;
	public void setSender(User sender) {
		// do sprawdzenia
		if( this.sender != null ) {
			if (this.sender.containsMyMessage(this)) {
				this.sender.removeMyMessage(this);
			}
		}
		this.sender = sender;
		sender.addMyMessage(this); // przypisanie powiązania
	}

	@Getter
	@Setter
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "messagesusers",
			joinColumns = { @JoinColumn(name = "messageID", referencedColumnName="messageID") },
			inverseJoinColumns = { @JoinColumn(name = "userID", referencedColumnName="userID") })
	private Set<User> receivers; // osoba lub osoby będące adresatami wiadomości - groupID trzeba zewnętrznie przetworzyć na listę osób
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
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinTable(name = "attachementsmessages",
			joinColumns = { @JoinColumn(name = "messageID", referencedColumnName="messageID") },
			inverseJoinColumns = { @JoinColumn(name = "fileID", referencedColumnName="fileID") })
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
		this.attachements.remove(attachement); // powinno powodować usunięcie testu z bazy (sprawdzić!)
	}

	@Getter
	@Setter
	@Column(name="isAnnouncement", nullable=false)
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean isAnnouncement;

	@Getter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="courseID", referencedColumnName="courseID")
	private Course course;
	public void setCourse(Course course) {
		// do sprawdzenia
		if( this.course != null ) {
			if (this.course.containsMessage(this)) {
				this.course.removeMessage(this);
			}
		}
		this.course = course;
		course.addMessage(this); // przypisanie powiązania
	}

	public Message() {
		super();
		this.receivers = new HashSet<>();
		this.attachements = new HashSet<>();
	}

}