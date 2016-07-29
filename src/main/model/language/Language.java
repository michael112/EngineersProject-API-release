package main.model.language;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;
import lombok.Setter;

import main.model.course.Course;
import main.model.placementtest.PlacementTest;
import main.model.user.User;

import main.model.abstracts.AbstractSinglePKModel;

@Entity
@Table(name="languages")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "languageID")) })
public class Language extends AbstractSinglePKModel<String> {

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="namedLanguage")
	private Set<LanguageName> languageNames;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.LAZY, mappedBy="language")
	private Set<Course> courses;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.LAZY, mappedBy="language")
	private Set<PlacementTest> placementTests;

	@Getter
	@Setter
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="taughtLanguages")
	private Set<User> teachers;


	public void addLanguageName(LanguageName languageName) {
		this.languageNames.add(languageName);
	}
	public void addLanguageName( Language namingLanguage, Language namedLanguage, String languageName ) {
		this.languageNames.add(new LanguageName(namingLanguage, namedLanguage, languageName));
	}
	public void addLanguageName( Language language, String languageName ) {
		this.languageNames.add(new LanguageName(language, languageName));
	}
	public void removeLanguageName(LanguageName languageName) {
		this.languageNames.remove(languageName);
	}


	public Language() {
		this.languageNames = new HashSet<>();
	}
	public Language( String languageID ) {
		this();
		this.setId(languageID);
	}
	public Language( String languageID, Set<LanguageName> languageNames ) {
		this(languageID);
		this.languageNames = languageNames;
	}
	
	// ===== zwraca nazwę języka w podanym języku =====
	public LanguageName getLanguageNameObj( String userLanguage ) {
		Iterator<LanguageName> iterator = this.languageNames.iterator();
		while( iterator.hasNext() ) {
			LanguageName element = iterator.next();
			if( userLanguage.equals( element.getNamingLanguage().getId() ) ) {
				return element;
			}
		}
		return null;
	}

	public String getLanguageName( String userLanguage ) {
		LanguageName result = getLanguageNameObj(userLanguage);
		return result != null ? result.getLanguageName() : null;
	}
	
}