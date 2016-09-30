package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseUserJson {

    @Getter
    private String userID;

    @Getter
    private String name;

    public CourseUserJson(String userID, String name) {
        this.userID = userID;
        this.name = name;
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString()) ) ) return false;
            CourseUserJson other = (CourseUserJson) otherObj;
            if( !( this.getUserID().equals(other.getUserID()) ) ) return false;
            if( !( this.getName().equals(other.getName()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
