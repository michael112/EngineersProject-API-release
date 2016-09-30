package main.json.user;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import main.model.user.userprofile.Phone;

public class EditPhoneJson {

    @Getter
    @Setter
    private Set<Phone> phone;

    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            EditPhoneJson other = (EditPhoneJson) otherObj;
            if( this.getPhone().size() != other.getPhone().size() ) return false;
            java.util.List<Phone> thisPhone = new java.util.ArrayList<>(this.getPhone());
            java.util.List<Phone> otherPhone = new java.util.ArrayList<>(other.getPhone());
            for( int i = 0; i < this.getPhone().size(); i++ ) {
                if( !( thisPhone.get(i).equals(otherPhone.get(i)) ) ) return false;
            }
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
