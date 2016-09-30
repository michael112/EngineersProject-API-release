package main.json.menu;

public class GuestMenuJson extends AbstractMenuJson {
    // todo

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            GuestMenuJson other = (GuestMenuJson) otherObj;
            // todo
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}