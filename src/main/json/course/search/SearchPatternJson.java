package main.json.course.search;

import lombok.Getter;
import lombok.Setter;

public class SearchPatternJson {

    @Getter
    @Setter
    private String language;

    @Getter
    @Setter
    private String courseType;

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            SearchPatternJson other = (SearchPatternJson) otherObj;
            if( !( this.getLanguage().equals(other.getLanguage()) ) ) return false;
            if( !( this.getCourseType().equals(other.getCourseType()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
