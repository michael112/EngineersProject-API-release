package main.model.placementtest;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;
import lombok.Setter;

import main.model.user.userprofile.PlacementTestResult;
import main.model.language.Language;
import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="placementTests")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "placementTestID")) })
public class PlacementTest extends AbstractUuidModel {

	@Getter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="languageID", referencedColumnName="languageID", nullable=false)
	private Language language;
	public void setLanguage(Language language) {
		// do sprawdzenia
		if( this.language != null ) {
			if (this.language.containsPlacementTest(this)) {
				this.language.changePlacementTestLanguage(this, language);
			}
		}
		this.language = language;
		language.addPlacementTest(this); // przypisanie powiązania
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="placementTestID", referencedColumnName="placementTestID", nullable=false)
	private Set<PlacementTask> tasks;
	public void setTasks(Set<PlacementTask> tasks) {
		if( tasks != null ) {
			this.tasks = tasks;
		}
		else {
			this.tasks = new HashSet<>();
		}
	}
	public void addTask(PlacementTask task) {
		if ( !( this.tasks.contains(task) ) ) {
			this.tasks.add(task);
		}
	}
	public void removeTask(PlacementTask task) {
		this.tasks.remove(task); // powinno powodować usunięcie z bazy (sprawdzić!)
	}
	public boolean containsTask(PlacementTask task) {
		return this.tasks.contains(task);
	}

	@Getter
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="test")
	private Set<PlacementTestResult> results;
	public void setResults(Set<PlacementTestResult> results) {
		if( results != null ) {
			this.results = results;
		}
		else {
			this.results = new HashSet<>();
		}
	}
	public void addResult(PlacementTestResult result) {
		if ( !( this.results.contains(result) ) ) {
			this.results.add(result);
		}
		if( result.getTest() != this ) {
			result.setTest(this); // przypisanie powiązania
		}
	}
	public void removeResult(PlacementTestResult result) {
		this.results.remove(result); // powinno powodować usunięcie testu z bazy (sprawdzić!)
	}
	public boolean containsResult(PlacementTestResult result) {
		return this.results.contains(result);
	}

	public PlacementTest() {
		super();
		this.tasks = new HashSet<>();
		this.results = new HashSet<>();
	}

	public PlacementTest(Language language, Set<PlacementTask> tasks) {
		this();
		this.setLanguage(language);
		this.setTasks(tasks);
	}

}