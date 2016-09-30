package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestJson {

    @Getter
    private String taskID;

    @Getter
    private String date;

    @Getter
    private String title;

    public TestJson(String taskID, String date, String title) {
        this.taskID = taskID;
        this.date = date;
        this.title = title;
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString()) ) ) return false;
            TestJson other = (TestJson) otherObj;
            if( !( this.getTaskID().equals(other.getTaskID()) ) ) return false;
            if( !( this.getDate().equals(other.getDate()) ) ) return false;
            if( !( this.getTitle().equals(other.getTitle()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
