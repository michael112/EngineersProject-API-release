package main.model.user.userprofile;

import javax.persistence.Embeddable;
import javax.persistence.Column;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class Address {

	@NotBlank(message = "address.street.empty")
	@Max(value = 50, message = "address.street.length")
	@Getter
	@Setter
	@Column(name="addressStreet", nullable=false)
	private String street;
	@NotBlank(message = "address.housenumber.empty")
	@Max(value = 5, message = "address.housenumber.length")
	@Getter
	@Setter
	@Column(name="addressHouseNumber", nullable=false)
	private String houseNumber;
	@Max(value = 5, message = "address.flatnumber.length")
	@Getter
	@Setter
	@Column(name="addressFlatNumber", nullable=true)
	private String flatNumber;
	@Max(value = 6, message = "address.postcode.length")
	@Getter
	@Setter
	@Column(name="addressPostCode", nullable=true)
	private String postCode;
	@NotBlank(message = "address.city.empty")
	@Max(value = 30, message = "address.city.length")
	@Getter
	@Setter
	@Column(name="addressCity", nullable=false)
	private String city;
	
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

	@Override
	public boolean equals(Object otherObj) {
		try {
			if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
			Address other = (Address) otherObj;
			if( !( this.getStreet().equals(other.getStreet()) ) ) return false;
			if( !( this.getHouseNumber().equals(other.getHouseNumber()) ) ) return false;
			if( ( this.getFlatNumber() != null ) || ( other.getFlatNumber() != null ) ) {
				if( !( this.getFlatNumber().equals(other.getFlatNumber()) ) ) return false;
			}
			if( ( this.getPostCode() != null ) || ( other.getPostCode() != null ) ) {
				if( !( this.getPostCode().equals(other.getPostCode()) ) ) return false;
			}
			if( !( this.getCity().equals(other.getCity()) ) ) return false;
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