package main.json.user;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditPasswordJson {

    @NotBlank(message = "oldpassword.empty")
    @Size(max = 100, message = "password.length")
    @Getter
    @Setter
    private String oldPassword;

    @NotBlank(message = "newpassword.empty")
    @Size(max = 100, message = "password.length")
    @Getter
    @Setter
    private String newPassword;

    @NotBlank(message = "confirmnewpassword.empty")
    @Size(max = 100, message = "password.length")
    @Getter
    @Setter
    private String newPasswordConfirm;

}
