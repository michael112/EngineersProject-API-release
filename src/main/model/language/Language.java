package main.model.language;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

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
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="namedLanguage", orphanRemoval=true)
	private Set<LanguageName> languageNames;
	public void setLanguageNames(Set<LanguageName> languageNames) {
		this.languageNames.clear();
		if( languageNames != null ) {
			this.languageNames.addAll(languageNames);
			for( LanguageName languageName : languageNames ) {
				if( ( languageName.getNamedLanguage() == null ) || ( !( languageName.getNamedLanguage().equals(this) ) ) ) {
					languageName.setNamedLanguage(this); // przypisanie powiązania
				}
			}
		}
	}
	public void addLanguageName(LanguageName languageName) {
		if( !( this.languageNames.contains(languageName) ) ) {
			this.languageNames.add(languageName);
		}
		if( languageName.getNamedLanguage() != this ) {
			languageName.setNamedLanguage(this); // przypisanie powiązania
		}
	}
	public void addLanguageName( Language namingLanguage, Language namedLanguage, String languageName ) {
		this.addLanguageName(new LanguageName(namingLanguage, namedLanguage, languageName));
	}
	public void addLanguageName( Language language, String languageName ) {
		this.addLanguageName(new LanguageName(language, languageName));
	}
	public void removeLanguageName(LanguageName languageName) {
		this.languageNames.remove(languageName);
	}
	public boolean containsLanguageName(LanguageName languageName) {
		return this.languageNames.contains(languageName);
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, mappedBy="language")
	private Set<Course> courses;
	public void setCourses(Set<Course> courses) {
		this.courses.clear();
		if( courses != null ) {
			this.courses.addAll(courses);
			for( Course course : courses ) {
				if( ( course.getLanguage() == null ) || ( !( course.getLanguage().equals(this) ) ) ) {
					course.setLanguage(this); // przypisanie powiązania
				}
			}
		}
	}
	public void addCourse(Course course) {
		if ( !( this.courses.contains(course) ) ) {
			this.courses.add(course);
		}
		if( course.getLanguage() != this ) {
			course.setLanguage(this); // przypisanie powiązania
		}
	}
	public boolean containsCourse(Course course) {
		return this.courses.contains(course);
	}
	public void changeCourseLanguage(Course course, Language newLanguage) {
		this.courses.remove(course);
		course.setLanguage(newLanguage);
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, mappedBy="language")
	private Set<PlacementTest> placementTests;
	public void setPlacementTests(Set<PlacementTest> placementTests) {
		this.placementTests.clear();
		if( placementTests != null ) {
			this.placementTests.addAll(placementTests);
			for( PlacementTest placementTest : placementTests ) {
				if( ( placementTest.getLanguage() == null ) || ( !( placementTest.getLanguage().equals(this) ) ) ) {
					placementTest.setLanguage(this); // przypisanie powiązania
				}
			}
		}
	}
	public void addPlacementTest(PlacementTest placementTest) {
		if ( !( this.placementTests.contains(placementTest) ) ) {
			this.placementTests.add(placementTest);
		}
		if( placementTest.getLanguage() != this ) {
			placementTest.setLanguage(this); // przypisanie powiązania
		}
	}
	public boolean containsPlacementTest(PlacementTest placementTest) {
		return this.placementTests.contains(placementTest);
	}
	public void changePlacementTestLanguage(PlacementTest placementTest, Language newLanguage) {
		this.placementTests.remove(placementTest);
		placementTest.setLanguage(newLanguage);
	}

	@Getter
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="taughtLanguages")
	private Set<User> teachers;
	public void setTeachers(Set<User> teachers) {
		this.teachers.clear();
		if( teachers != null ) {
			this.teachers.addAll(teachers);
			for( User teacher : teachers ) {
				if( !( teacher.containsTaughtLanguage(this) ) ) {
					teacher.addTaughtLanguage(this); // przypisanie powiązania
				}
			}
		}
	}
	public void addTeacher(User teacher) {
		if ( !( this.teachers.contains(teacher) ) ) {
			this.teachers.add(teacher);
		}
		if( !( teacher.containsTaughtLanguage(this) ) ) {
			teacher.addTaughtLanguage(this); // przypisanie powiązania
		}
	}
	public void removeTeacher(User teacher) {
		this.teachers.remove(teacher);
		if( teacher.containsTaughtLanguage(this) ) {
			teacher.removeTaughtLanguage(this);
		}
	}
	public boolean containsTeacher(User teacher) {
		return this.teachers.contains(teacher);
	}

	public Language() {
		super();
		this.languageNames = new HashSet<>();
		this.courses = new HashSet<>();
		this.placementTests = new HashSet<>();
		this.teachers = new HashSet<>();
	}
	public Language( String languageID ) {
		this();
		this.setId(languageID);
	}
	public Language( String languageID, Set<LanguageName> languageNames ) {
		this(languageID);
		this.languageNames = languageNames;
	}

	public boolean hasLocale() {
		return java.util.Arrays.asList(Locale.getISOLanguages()).contains(new Locale(super.getId()).getLanguage());
	}

	public Locale toLocale() {
		if( !( this.hasLocale() ) ) {
			return null;
		}
		else {
			return new Locale(super.getId());
		}
	}

	// ===== zwracają nazwę języka w podanym języku =====

	public LanguageName getLanguageNameObj( String userLanguage ) {
		Iterator<LanguageName> iterator = this.languageNames.iterator();
		while( iterator.hasNext() ) {
			LanguageName element = iterator.next();
			if( userLanguage.equalsIgnoreCase( element.getNamingLanguage().getId() ) ) {
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