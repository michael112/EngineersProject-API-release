package main.json.user;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.model.user.userprofile.Phone;

@EqualsAndHashCode
public class EditPhoneJson {

    @Getter
    @Setter
    private Set<Phone> phone;

}
