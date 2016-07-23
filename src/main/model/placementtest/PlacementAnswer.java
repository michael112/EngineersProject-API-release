package main.model.placementtest;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.AbstractModel;

@Entity
@Table(name="placementAnswers")
public class PlacementAnswer extends AbstractModel<String> {
	
	@Getter
	@Setter
	@Id
	@Column(name="placementAnswerID")
	private String id;

	@Getter
	@Setter
	@Column(name="answerKey", nullable=false)
	private String answerKey;

	@Getter
	@Setter
	@Column(name="answerName", nullable=false)
	private String answerName;

	public PlacementAnswer() {
		this.id = new UUID().toString();
	}

}