package main.json.menu;

public class AdminMenuJson extends AbstractMenuJson {
    // todo

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            AdminMenuJson other = (AdminMenuJson) otherObj;
            // todo
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}