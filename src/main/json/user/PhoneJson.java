package main.json.user;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import org.codehaus.jackson.annotate.JsonIgnore;

import main.model.user.userprofile.Phone;
import main.model.enums.PhoneType;

import main.constants.validationconstants.ValidationConstants;

public class PhoneJson {

    @JsonIgnore
    private Phone phone;

    @Pattern(regexp = ValidationConstants.UUID_REGEX, message = "phone.id.invalid")
    @Size(max = 36, message = "phone.id.length")
    public String getPhoneId() {
        return this.phone.getId();
    }
    public void setPhoneId(String phoneId) {
        this.phone.setId(phoneId);
    }

    @Pattern(regexp = ValidationConstants.PHONE_REGEX, message = "phone.type.invalid")
    @NotBlank(message = "phone.type.empty")
    @Size(max = 8, message = "phone.type.length")
    public String getPhoneType() {
        return this.phone.getPhoneType().name();
    }
    public void setPhoneType(String phoneType) {
        this.phone.setPhoneType(PhoneType.valueOf(phoneType));
    }


    @NotBlank(message = "phone.number.empty")
    @Size(min = ValidationConstants.PHONE_NUMBER_MIN, max = ValidationConstants.PHONE_NUMBER_MAX, message = "phone.number.length")
    public String getPhoneNumber() {
        return this.phone.getPhoneNumber();
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phone.setPhoneNumber(phoneNumber);
    }

    public Phone toObject() {
        return this.phone;
    }

    public PhoneJson() {
        this.phone = new Phone();
    }

    public PhoneJson(Phone phone) {
        this.phone = phone;
    }

}
