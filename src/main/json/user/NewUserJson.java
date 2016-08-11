package main.json.user;

import java.util.Set;
import java.util.HashSet;

import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;

import lombok.Getter;
import lombok.Setter;

public class NewUserJson {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String passwordConfirm;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private Set<Phone> phone;

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
