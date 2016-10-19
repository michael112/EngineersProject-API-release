package main.json.user;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditPasswordJson {

    @NotBlank(message = "oldpassword.empty")
    @Max(value = 100, message = "password.length")
    @Getter
    @Setter
    private String oldPassword;

    @NotBlank(message = "newpassword.empty")
    @Max(value = 100, message = "password.length")
    @Getter
    @Setter
    private String newPassword;

    @NotBlank(message = "confirmnewpassword.empty")
    @Max(value = 100, message = "password.length")
    @Getter
    @Setter
    private String newPasswordConfirm;

}
