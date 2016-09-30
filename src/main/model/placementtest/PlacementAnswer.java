package main.model.placementtest;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;
import lombok.Setter;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="placementAnswers")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "placementAnswerID")) })
public class PlacementAnswer extends AbstractUuidModel {

	@Getter
	@Setter
	@Column(name="answerKey", nullable=false)
	private String answerKey;

	@Getter
	@Setter
	@Column(name="answerName", nullable=false)
	private String answerName;

	public PlacementAnswer() {
		super();
	}

	public PlacementAnswer(String answerKey, String answerName) {
		this();
		this.setAnswerKey(answerKey);
		this.setAnswerName(answerName);
	}

	@Override
	public boolean equals(Object otherObj) {
		try {
			if ( !( otherObj.getClass().toString().equals(this.getClass().toString())) ) return false;
			PlacementAnswer other = (PlacementAnswer) otherObj;
			if( !( this.getId().equals(other.getId()) ) ) return false;
			if( !( this.getAnswerKey().equals(other.getAnswerKey()) ) ) return false;
			if( !( this.getAnswerName().equals(other.getAnswerName()) ) ) return false;
			return true;
		}
		catch( NullPointerException ex ) {
			return false;
		}
	}
}