package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseTypeJson {

    @Getter
    private String courseTypeID;

    @Getter
    private String name;


    public CourseTypeJson(String courseTypeID, String name) {
        this.courseTypeID = courseTypeID;
        this.name = name;
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString()) ) ) return false;
            CourseTypeJson other = (CourseTypeJson) otherObj;
            if( !( this.getCourseTypeID().equals(other.getCourseTypeID()) ) ) return false;
            if( !( this.getName().equals(other.getName()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
