package main.json.user;

import java.util.Set;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PhoneJsonSet {

    @Valid
    @Getter
    @Setter
    private Set<PhoneJson> phone;

}
