package main.json.user;

import java.util.Set;
import java.util.HashSet;

import javax.validation.Valid;
import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NewUserJson {

    @NotBlank(message = "username.empty")
    @Max(value = 30, message = "username.length")
    @Getter
    @Setter
    private String username;

    @NotBlank(message = "password.empty")
    @Max(value = 100, message = "password.length")
    @Getter
    @Setter
    private String password;

    @NotBlank(message = "passwordconfirm.empty")
    @Max(value = 100, message = "password.length")
    @Getter
    @Setter
    private String passwordConfirm;

    @NotBlank(message = "firstname.empty")
    @Max(value = 30, message = "firstname.length")
    @Getter
    @Setter
    private String firstName;

    @NotBlank(message = "lastname.empty")
    @Max(value = 30, message = "lastname.length")
    @Getter
    @Setter
    private String lastName;

    @NotBlank(message = "email.empty")
    @Email(message = "email.invalid")
    @Max(value = 50, message = "email.length")
    @Getter
    @Setter
    private String email;

    @Valid
    @Getter
    @Setter
    private Set<Phone> phone;

    @Valid
    @Getter
    @Setter
    private Address address;

    public void addPhone(Phone phone) {
        this.phone.add(phone);
    }
    public void removePhone(Phone phone) {
        this.phone.remove(phone);
    }

    public NewUserJson() {
        this.phone = new HashSet<>();
    }
}
