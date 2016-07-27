package main.model.placementtest;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

import lombok.Getter;
import lombok.Setter;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="placementSentences")
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
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="placementSentenceID", referencedColumnName="placementSentenceID")
	private Set<PlacementAnswer> answers;

	@Getter
	@Setter
	@Column(name="correctAnswer", nullable=true)
	private String correctAnswer;

	public PlacementSentence() {
		super();
	}

}