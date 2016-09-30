package main.json.user;

import java.util.Set;
import java.util.HashSet;

import main.model.user.userprofile.Address;
import main.model.user.userprofile.Phone;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NewUserJson {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String passwordConfirm;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private Set<Phone> phone;

    @Getter
    @Setter
    private Address address;

    public void addPhone(Phone phone) {
        this.phone.add(phone);
    }
    public void removePhone(Phone phone) {
        this.phone.remove(phone);
    }

    public NewUserJson() {
        this.phone = new HashSet<>();
    }

    /*
    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            NewUserJson other = (NewUserJson) otherObj;
            if( !( this.getUsername().equals(other.getUsername()) ) ) return false;
            if( !( this.getPassword().equals(other.getPassword()) ) ) return false;
            if( !( this.getPasswordConfirm().equals(other.getPasswordConfirm()) ) ) return false;
            if( !( this.getFirstName().equals(other.getFirstName()) ) ) return false;
            if( !( this.getLastName().equals(other.getLastName()) ) ) return false;
            if( !( this.getEmail().equals(other.getEmail()) ) ) return false;
            if( this.getPhone().size() != other.getPhone().size() ) return false;
            java.util.List<Phone> thisPhone = new java.util.ArrayList<>(this.getPhone());
            java.util.List<Phone> otherPhone = new java.util.ArrayList<>(other.getPhone());
            for( int i = 0; i < this.getPhone().size(); i++ ) {
                if( !( thisPhone.get(i).equals(otherPhone.get(i)) ) ) return false;
            }
            if( !( this.getAddress().equals(other.getAddress()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
    */
}
