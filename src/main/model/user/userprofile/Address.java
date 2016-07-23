package main.model.user.userprofile;

import javax.persistence.Embeddable;
import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class Address {

	// ===== fields =====
	
	@Getter
	@Setter
	@Column(name="addressStreet", nullable=false)
	private String street;
	@Getter
	@Setter
	@Column(name="addressHouseNumber", nullable=false)
	private String houseNumber;
	@Getter
	@Setter
	@Column(name="addressFlatNumber", nullable=true)
	private String flatNumber;
	@Getter
	@Setter
	@Column(name="addressPostCode", nullable=true)
	private String postCode;
	@Getter
	@Setter
	@Column(name="addressCity", nullable=false)
	private String city;
	
	// ===== constructors =====

	public Address( String street, String houseNumber, String postCode, String city ) {
		this.street = street;
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.city = city;
		this.flatNumber = null;
	}

	public Address( String street, String houseNumber, String flatNumber, String postCode, String city ) {
		this(street, houseNumber, postCode, city);
		this.flatNumber = flatNumber;
	}

	public Address() {}

}