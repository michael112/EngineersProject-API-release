package main.model.course;

import java.util.Set;

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

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="messages")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "messageID")) })
public class Message extends AbstractUuidModel {

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="senderID", referencedColumnName="userID")
	private User sender;

	@Getter
	@Setter
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "messagesusers",
			joinColumns = { @JoinColumn(name = "messageID", referencedColumnName="messageID") },
			inverseJoinColumns = { @JoinColumn(name = "userID", referencedColumnName="userID") })
	private Set<User> receivers; // osoba lub osoby będące adresatami wiadomości - groupID trzeba zewnętrznie przetworzyć na listę osób

	@Getter
	@Setter
	@Column(name="title", nullable=false)
	private String title;

	@Getter
	@Setter
	@Column(name="content", nullable=false)
	private String content;

	@Getter
	@Setter
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinTable(name = "attachementsmessages",
			joinColumns = { @JoinColumn(name = "messageID", referencedColumnName="messageID") },
			inverseJoinColumns = { @JoinColumn(name = "fileID", referencedColumnName="fileID") })
	private Set<File> attachements;

	@Getter
	@Setter
	@Column(name="isAnnouncement", nullable=false)
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean isAnnouncement;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="courseID", referencedColumnName="courseID")
	private Course course; // mapowanie poprzez courseID

	public Message() {
		super();
	}

}