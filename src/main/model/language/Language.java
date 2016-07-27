package main.model.language;

import java.util.Set;
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

import lombok.Getter;
import lombok.Setter;

import main.model.course.Course;
import main.model.placementtest.PlacementTest;
import main.model.user.User;

import main.model.abstracts.AbstractModel;

@Entity
@Table(name="languages")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "languageID")) })
public class Language extends AbstractModel<String> {

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
	
	// ===== constructor =====
	
	public Language( String languageID, Set<LanguageName> languageNames ) {
		this.setId(languageID);
		this.languageNames = languageNames;
	}
	public Language() {}
	
	// ===== zwraca nazwę języka w podanym języku =====
	public String getLanguageName( String userLanguage ) {
		Iterator<LanguageName> iterator = this.languageNames.iterator();
		while( iterator.hasNext() ) {
			LanguageName element = iterator.next();
			if( userLanguage.equals( element.getNamingLanguage().getId() ) ) {
				return element.getLanguageName();
			}
		}
		return null;
	}
	
}