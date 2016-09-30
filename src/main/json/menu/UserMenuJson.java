package main.json.menu;

public class UserMenuJson extends AbstractMenuJson {
    // todo

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            UserMenuJson other = (UserMenuJson) otherObj;
            // todo
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}