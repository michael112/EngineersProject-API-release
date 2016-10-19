package main.model.user.userprofile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotBlank;

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

import main.constants.validationconstants.ValidationConstants;

@Entity
@Table(name="phones")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "phoneID")) })
public class Phone extends AbstractUuidModel {

	@Pattern(regexp = ValidationConstants.PHONE_REGEX, message = "phonetype.invalid")
	@NotBlank(message = "phonetype.empty")
	@Max(value = 8, message = "phonetype.length")
	@Getter
	@Setter
	@Column(name="phoneType", nullable=false)
	@Enumerated(EnumType.STRING)
	private PhoneType phoneType;
	@NotBlank(message = "phonenumber.empty")
	@Size(min = ValidationConstants.PHONE_NUMBER_MIN, max = ValidationConstants.PHONE_NUMBER_MAX, message = "phonenumber.length")
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

	@Override
	public boolean equals(Object otherObj) {
		try {
			if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
			Phone other = (Phone) otherObj;
			if( !( this.getId().equals(other.getId()) ) ) return false;
			if( !( this.getPhoneType().equals(other.getPhoneType()) ) ) return false;
			if( !( this.getPhoneNumber().equals(other.getPhoneNumber()) ) ) return false;
			return true;
		}
		catch( NullPointerException ex ) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}