package main.model.placementtest;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

import lombok.Getter;
import lombok.Setter;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="placementAnswers")
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

}