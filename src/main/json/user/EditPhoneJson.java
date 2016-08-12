package main.json.user;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import main.model.user.userprofile.Phone;

public class EditPhoneJson {

    @Getter
    @Setter
    private Set<Phone> phone;

}
