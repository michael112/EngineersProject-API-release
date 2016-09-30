package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class HomeworkJson {
    @Getter
    private String homeworkID;

    @Getter
    private String date;

    @Getter
    private String title;

    public HomeworkJson(String homeworkID, String date, String title) {
        this.homeworkID = homeworkID;
        this.date = date;
        this.title = title;
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString()) ) ) return false;
            HomeworkJson other = (HomeworkJson) otherObj;
            if( !( this.getHomeworkID().equals(other.getHomeworkID()) ) ) return false;
            if( !( this.getDate().equals(other.getDate()) ) ) return false;
            if( !( this.getTitle().equals(other.getTitle()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
