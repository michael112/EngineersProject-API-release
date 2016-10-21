package main.model.placementtest;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Access;
import javax.persistence.AccessType;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="placementAnswers")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "placementAnswerID")) })
public class PlacementAnswer extends AbstractUuidModel {

	@NotBlank(message = "solvedplacementanswer.answerkey.empty")
	@Size(max = 1, message = "solvedplacementanswer.answerkey.length")
	@Getter
	@Setter
	@Column(name="answerKey", nullable=false)
	private String answerKey;

	@NotBlank(message = "solvedplacementanswer.answername.empty")
	@Size(max = 30, message = "solvedplacementanswer.answername.length")
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

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}