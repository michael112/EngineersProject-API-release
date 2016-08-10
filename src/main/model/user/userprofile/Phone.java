package main.model.user.userprofile;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Getter;
import lombok.Setter;

import main.model.abstracts.AbstractUuidModel;

import main.model.enums.PhoneType;

@Entity
@Table(name="phones")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "phoneID")) })
public class Phone extends AbstractUuidModel {

	@Getter
	@Setter
	@Column(name="phoneType", nullable=false)
	@Enumerated(EnumType.STRING)
	private PhoneType phoneType;
	@Getter
	@Setter
	@Column(name="phoneNumber", nullable=false)
	private String phoneNumber;

	public Phone() {
		super();
	}

	public Phone(PhoneType phoneType, String phoneNumber) {
		this();
		this.setPhoneType(phoneType);
		this.setPhoneNumber(phoneNumber);
	}

}