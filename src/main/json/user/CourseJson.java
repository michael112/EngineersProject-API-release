package main.json.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseJson {

    @Getter
    private String id;
    @Getter
    private String language;
    @Getter
    private String level;
    @Getter
    private Boolean confirmed;


    public CourseJson(String id, String language, String level) {
        this.id = id;
        this.language = language;
        this.level = level;
        this.confirmed = null;
    }
    public CourseJson(String id, String language, String level, boolean confirmed) {
        this(id, language, level);
        this.confirmed = confirmed;
    }

    /*
    @Override
    public boolean equals(Object otherObj) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            CourseJson other = (CourseJson) otherObj;
            if( !( this.getId().equals(other.getId()) ) ) return false;
            if( !( this.getLanguage().equals(other.getLanguage()) ) ) return false;
            if( !( this.getLevel().equals(other.getLevel()) ) ) return false;
            if( !( this.getConfirmed().equals(other.getConfirmed()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
    */
}
