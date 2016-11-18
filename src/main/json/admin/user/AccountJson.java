package main.json.admin.user;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.json.user.PhoneJson;
import main.model.user.userprofile.Address;

@EqualsAndHashCode
public class AccountJson {

    @Setter
    @Getter
    private String username;

    @Setter
    @Getter
    private String firstName;

    @Setter
    @Getter
    private String lastName;

    @Setter
    @Getter
    private String eMail;

    @Setter
    @Getter
    private Set<PhoneJson> phone;

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
