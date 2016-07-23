package main.model.language;

import java.util.Set;
import java.util.Iterator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import lombok.Getter;
import lombok.Setter;

import main.model.course.Course;
import main.model.placementtest.PlacementTest;
import main.model.user.User;

import main.model.AbstractModel;

@Entity
@Table(name="languages")
public class Language extends AbstractModel<String> {
	
	// ===== fields =====

	@Getter
	@Setter
	@Id
	@Column(name="languageID")
	private String id;

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
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="taughtlanguages")
	private Set<User> teachers;
	
	// ===== constructor =====
	
	public Language( String languageID, Set<LanguageName> languageNames ) {
		this.id = languageID;
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