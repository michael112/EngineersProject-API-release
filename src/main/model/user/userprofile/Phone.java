package main.model.user.userprofile;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;


import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.AbstractModel;
import main.model.enums.PhoneType;

@Entity
@Table(name="phones")
public class Phone extends AbstractModel<String> {
	
	// ===== fields =====
	
	@Getter
	@Setter
	@Id
	@Column(name="phoneID")
	private String id;
	@Getter
	@Setter
	@Column(name="phoneType", nullable=false)
	private PhoneType phoneType;
	@Getter
	@Setter
	@Column(name="phoneNumber", nullable=false)
	private String phoneNumber;
	
	// ===== constructors =====

	public Phone( PhoneType phoneType, String phoneNumber ) {
		this();
		this.phoneType = phoneType;
		this.phoneNumber = phoneNumber;
	}
	
	public Phone() {
		this.id = new UUID().toString();
	}

}