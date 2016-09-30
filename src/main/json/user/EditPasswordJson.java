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

    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            EditPasswordJson other = (EditPasswordJson) otherObj;
            if( !( this.getOldPassword().equals(other.getOldPassword()) ) ) return false;
            if( !( this.getNewPassword().equals(other.getNewPassword()) ) ) return false;
            if( !( this.getNewPasswordConfirm().equals(other.getNewPasswordConfirm()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
