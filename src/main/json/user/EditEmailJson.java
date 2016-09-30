package main.json.user;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditEmailJson {

    @Getter
    @Setter
    private String newEmail;

    /*
    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            EditEmailJson other = (EditEmailJson) otherObj;
            if( !( this.getNewEmail().equals(other.getNewEmail()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
    */
}
