package main.json.admin.user;

import java.util.Set;
import java.util.HashSet;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.json.user.PhoneJson;
import main.model.user.userprofile.Address;

@EqualsAndHashCode
public class AccountJson {

    @NotBlank(message = "username.empty")
    @Size(max = 30, message = "username.length")
    @Setter
    @Getter
    private String username;

    @NotBlank(message = "firstname.empty")
    @Size(max = 30, message = "firstname.length")
    @Setter
    @Getter
    private String firstName;

    @NotBlank(message = "lastname.empty")
    @Size(max = 30, message = "lastname.length")
    @Setter
    @Getter
    private String lastName;

    @NotBlank(message = "email.empty")
    @Email(message = "email.invalid")
    @Size(max = 50, message = "email.length")
    @Setter
    @Getter
    private String eMail;

    @Valid
    @Setter
    @Getter
    private Set<PhoneJson> phone;

    @Valid
    @Setter
    @Getter
    private Address address;

    public void addPhone(PhoneJson phone) {
        this.phone.add(phone);
    }

    public AccountJson() {
        super();
        this.phone = new HashSet<>();
    }

    public AccountJson(String username, String firstName, String lastName, String eMail, Address address) {
        this();
        this.setUsername(username);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEMail(eMail);
        this.setAddress(address);
    }

}
