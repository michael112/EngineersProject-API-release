package main.json.user;

import java.util.Set;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.model.user.userprofile.Phone;

@EqualsAndHashCode
public class EditPhoneJson {

    @Valid
    @Getter
    @Setter
    private Set<Phone> phone;

}
