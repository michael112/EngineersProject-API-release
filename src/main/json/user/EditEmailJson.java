package main.json.user;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditEmailJson {

    @Getter
    @Setter
    private String newEmail;

}
