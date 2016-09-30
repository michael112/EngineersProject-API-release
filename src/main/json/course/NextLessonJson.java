package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NextLessonJson {

    @Getter
    private String day;

    @Getter
    private String hour;

    public NextLessonJson(String day, String hour) {
        this.day = day;
        this.hour = hour;
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString()) ) ) return false;
            NextLessonJson other = (NextLessonJson) otherObj;
            if( !( this.getDay().equals(other.getDay()) ) ) return false;
            if( !( this.getHour().equals(other.getHour()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
