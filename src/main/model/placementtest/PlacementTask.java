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
@Table(name="placementTasks")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "placementTaskID")) })
public class PlacementTask extends AbstractUuidModel {

	@Getter
	@Setter
	@Column(name="command", nullable=false)
	private String command;

	@Getter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="placementTaskID", referencedColumnName="placementTaskID", nullable=false)
	private Set<PlacementSentence> sentences;
	public void setSentences(Set<PlacementSentence> sentences) {
		if( sentences != null ) {
			this.sentences = sentences;
		}
		else {
			this.sentences = new HashSet<>();
		}
	}
	public void addSentence(PlacementSentence sentence) {
		if ( !( this.sentences.contains(sentence) ) ) {
			this.sentences.add(sentence);
		}
	}
	public void removeSentence(PlacementSentence sentence) {
		this.sentences.remove(sentence); // powinno powodować usunięcie z bazy (sprawdzić!)
	}
	public boolean containsSentence(PlacementSentence sentence) {
		return this.sentences.contains(sentence);
	}
	
	public PlacementTask() {
		super();
		this.sentences = new HashSet<>();
	}
	public PlacementTask(String command, Set<PlacementSentence> sentences) {
		this();
		this.setCommand(command);
		this.setSentences(sentences);
	}
}