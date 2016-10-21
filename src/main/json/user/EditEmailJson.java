package main.json.user;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditEmailJson {

    @NotBlank(message = "email.empty")
    @Email(message = "email.invalid")
    @Size(max = 50, message = "email.length")
    @Getter
    @Setter
    private String newEmail;

}
