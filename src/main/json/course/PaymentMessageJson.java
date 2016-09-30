package main.json.course;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.NONE)
public class PaymentMessageJson {
    // todo

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            PaymentMessageJson other = (PaymentMessageJson) otherObj;
            // todo
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }

    }
}
