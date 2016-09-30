package main.json.course;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageJson {

    @Getter
    private String id;

    @Getter
    private String name;

    public LanguageJson(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString()) ) ) return false;
            LanguageJson other = (LanguageJson) otherObj;
            if( !( this.getId().equals(other.getId()) ) ) return false;
            if( !( this.getName().equals(other.getName()) ) ) return false;
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
