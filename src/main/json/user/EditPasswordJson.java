package main.json.user;

import lombok.Getter;
import lombok.Setter;

public class EditPasswordJson {

    @Getter
    @Setter
    private String oldPassword;

    @Getter
    @Setter
    private String newPassword;

    @Getter
    @Setter
    private String newPasswordConfirm;

}
