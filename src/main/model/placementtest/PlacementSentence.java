package main.model.placementtest;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
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

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="placementSentences")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "placementSentenceID")) })
public class PlacementSentence extends AbstractUuidModel {

	@Getter
	@Setter
	@Column(name="prefix", nullable=true)
	private String prefix;

	@Getter
	@Setter
	@Column(name="suffix", nullable=true)
	private String suffix;

	@Getter
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, orphanRemoval=true)
	@JoinColumn(name="placementSentenceID", referencedColumnName="placementSentenceID", nullable=false)
	private Set<PlacementAnswer> answers;
	public void setAnswers(Set<PlacementAnswer> answers) {
		this.answers.clear();
		if( answers != null ) {
			this.answers.addAll(answers);
		}
	}
	public void addAnswer(PlacementAnswer answer) {
		if ( !( this.answers.contains(answer) ) ) {
			this.answers.add(answer);
		}
	}
	public void removeAnswer(PlacementAnswer answer) {
		this.answers.remove(answer);
	}
	public boolean containsAnswer(PlacementAnswer answer) {
		return this.answers.contains(answer);
	}

	@Getter
	@Setter
	@Column(name="correctAnswer", nullable=true)
	private String correctAnswer;

	public PlacementSentence() {
		super();
		this.answers = new HashSet<>();
	}

	public PlacementSentence(String prefix, String suffix, Set<PlacementAnswer> answers, String correctAnswer) {
		this();
		this.setPrefix(prefix);
		this.setSuffix(suffix);
		this.setAnswers(answers);
		this.setCorrectAnswer(correctAnswer);
	}

	@Override
	public boolean equals(Object otherObj) {
		try {
			if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
			PlacementSentence other = (PlacementSentence) otherObj;
			if( !( this.getId().equals(other.getId()) ) ) return false;
			if( !( this.getPrefix().equals(other.getPrefix()) ) ) return false;
			if( !( this.getSuffix().equals(other.getSuffix()) ) ) return false;
			if( this.getAnswers().size() != other.getAnswers().size() ) return false;
			java.util.List<PlacementAnswer> thisAnswers = new java.util.ArrayList<>(this.getAnswers());
			java.util.List<PlacementAnswer> otherAnswers = new java.util.ArrayList<>(other.getAnswers());
			for( int i = 0; i < this.getAnswers().size(); i++ ) {
				if( !( thisAnswers.get(i).equals(otherAnswers.get(i)) ) ) return false;
			}
			if( !( this.getCorrectAnswer().equals(other.getCorrectAnswer()) ) ) return false;
			return true;
		}
		catch( NullPointerException ex ) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}