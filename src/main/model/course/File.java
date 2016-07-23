package main.model.course;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;

import main.model.AbstractModel;

@Entity
@Table(name="files")
public class File extends AbstractModel<String> {
	
	// ===== fields =====
	@Getter
	@Setter
	@Id
	@Column(name="fileID")
	private String id;

	@Getter
	@Setter
	@Column(name="name", nullable=false)
	private String name;

	@Getter
	@Setter
	@Column(name="date", nullable=false)
	private Date date;

	@Getter
	@Setter
	@Column(name="path", nullable=false)
	private String path;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="senderID", referencedColumnName="userID", nullable=false)
	private User sender;

	public File() {
		this.id = new UUID().toString();
	}

}