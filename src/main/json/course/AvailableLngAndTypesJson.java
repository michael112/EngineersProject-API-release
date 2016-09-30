package main.json.course;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvailableLngAndTypesJson {

    @Getter
    private Set<LanguageJson> languages;

    @Getter
    private Set<CourseTypeJson> types;

    public void addLanguage(String id, String languageName) {
        this.addLanguage(new LanguageJson(id, languageName));
    }
    private void addLanguage(LanguageJson language) {
        this.languages.add(language);
    }

    public void addType(String courseTypeID, String courseTypeName) {
        this.addType(new CourseTypeJson(courseTypeID, courseTypeName));
    }
    private void addType(CourseTypeJson courseType) {
        this.types.add(courseType);
    }

    public AvailableLngAndTypesJson() {
        this.languages = new HashSet<>();
        this.types = new HashSet<>();
    }

    @Override
    public boolean equals( Object otherObj ) {
        try {
            if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
            AvailableLngAndTypesJson other = (AvailableLngAndTypesJson) otherObj;
            if( this.getLanguages().size() != other.getLanguages().size() ) return false;
            java.util.List<LanguageJson> thisLanguages = new java.util.ArrayList<>(this.getLanguages());
            java.util.List<LanguageJson> otherLanguages = new java.util.ArrayList<>(other.getLanguages());
            for( int i = 0; i < this.getLanguages().size(); i++ ) {
                if( !( thisLanguages.get(i).equals(otherLanguages.get(i)) ) ) return false;
            }
            if( this.getTypes().size() != other.getTypes().size() ) return false;
            java.util.List<CourseTypeJson> thisTypes = new java.util.ArrayList<>(this.getTypes());
            java.util.List<CourseTypeJson> otherTypes = new java.util.ArrayList<>(other.getTypes());
            for( int i = 0; i < this.getTypes().size(); i++ ) {
                if( !( thisTypes.get(i).equals(otherTypes.get(i)) ) ) return false;
            }
            return true;
        }
        catch( NullPointerException ex ) {
            return false;
        }
    }
}
