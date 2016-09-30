package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageJson {

    @Getter
    private String messageID;

    @Getter
    private String title;

    public MessageJson(String messageID, String title) {
        this.messageID = messageID;
        this.title = title;
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString()) ) ) return false;
            MessageJson other = (MessageJson) otherObj;
            if( !( this.getMessageID().equals(other.getMessageID()) ) ) return false;
            if( !( this.getTitle().equals(other.getTitle()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
