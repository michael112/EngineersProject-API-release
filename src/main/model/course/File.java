package main.model.course;

import org.joda.time.DateTime;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="files")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "fileID")) })
public class File extends AbstractUuidModel {

	@Getter
	@Setter
	@Column(name="name", nullable=true)
	private String name;

	@Getter
	@Setter
	@Column(name="date", nullable=false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime date;

	@Getter
	@Setter
	@Column(name="path", nullable=false)
	private String path;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="senderID", referencedColumnName="userID", nullable=false)
	private User sender;

	public File() {
		super();
	}

	public File(String name, DateTime date, String path, User sender) {
		this();
		this.setName(name);
		this.setDate(date);
		this.setPath(path);
		this.setSender(sender);
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