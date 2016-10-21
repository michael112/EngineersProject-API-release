package main.json.user;

import java.util.Set;
import java.util.HashSet;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.model.user.userprofile.Address;

@EqualsAndHashCode
public class NewUserJson {

    @NotBlank(message = "username.empty")
    @Size(max = 30, message = "username.length")
    @Getter
    @Setter
    private String username;

    @NotBlank(message = "password.empty")
    @Size(max = 100, message = "password.length")
    @Getter
    @Setter
    private String password;

    @NotBlank(message = "passwordconfirm.empty")
    @Size(max = 100, message = "password.length")
    @Getter
    @Setter
    private String passwordConfirm;

    @NotBlank(message = "firstname.empty")
    @Size(max = 30, message = "firstname.length")
    @Getter
    @Setter
    private String firstName;

    @NotBlank(message = "lastname.empty")
    @Size(max = 30, message = "lastname.length")
    @Getter
    @Setter
    private String lastName;

    @NotBlank(message = "email.empty")
    @Email(message = "email.invalid")
    @Size(max = 50, message = "email.length")
    @Getter
    @Setter
    private String email;

    @Valid
    @Getter
    @Setter
    private Set<PhoneJson> phone;

    @Valid
    @Getter
    @Setter
    private Address address;

    public void addPhone(PhoneJson phone) {
        this.phone.add(phone);
    }
    public void removePhone(PhoneJson phone) {
        this.phone.remove(phone);
    }

    public NewUserJson() {
        this.phone = new HashSet<>();
    }
}
