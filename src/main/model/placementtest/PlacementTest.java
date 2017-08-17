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
		if( language != null ) {
			if (this.language != null) {
				if (this.language.containsPlacementTest(this)) {
					this.language.changePlacementTestLanguage(this, language);
				}
			}
			this.language = language;
			if (language != null) language.addPlacementTest(this); // przypisanie powiązania
		}
		else throw new IllegalArgumentException();
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, orphanRemoval=true)
	@JoinColumn(name="placementTestID", referencedColumnName="placementTestID", nullable=false)
	private Set<PlacementTask> tasks;
	public void setTasks(Set<PlacementTask> tasks) {
		this.tasks.clear();
		if( tasks != null ) {
			this.tasks.addAll(tasks);
		}
	}
	public void addTask(PlacementTask task) {
		if ( !( this.tasks.contains(task) ) ) {
			this.tasks.add(task);
		}
	}
	public void removeTask(PlacementTask task) {
		this.tasks.remove(task);
	}
	public boolean containsTask(PlacementTask task) {
		return this.tasks.contains(task);
	}

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="test", orphanRemoval=true)
	private Set<PlacementTestResult> results;
	public void setResults(Set<PlacementTestResult> results) {
		this.results.clear();
		if( results != null ) {
			this.results.addAll(results);
			for( PlacementTestResult result : results ) {
				if( ( result.getTest() == null ) || ( !( result.getTest().equals(this) ) ) ) {
					result.setTest(this); // przypisanie powiązania
				}
			}
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
		this.results.remove(result);
	}
	public boolean containsResult(PlacementTestResult result) {
		return this.results.contains(result);
	}

	public PlacementSentence getSentence(String sentenceID) {
		for( PlacementTask task : this.getTasks() ) {
			for( PlacementSentence sentence : task.getSentences() ) {
				if( sentence.getId().equals(sentenceID) ) {
					return sentence;
				}
			}
		}
		return null;
	}

	public double getMaxResult() {
		double result = 0;
		for( PlacementTask task : this.getTasks() ) {
			for( PlacementSentence sentence : task.getSentences() ) {
				result++;
			}
		}
		return result;
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

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object otherObj) {
		return super.equals(otherObj);
	}

}