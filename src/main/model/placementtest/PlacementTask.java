package main.model.placementtest;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.AbstractModel;

@Entity
@Table(name="placementTasks")
public class PlacementTask extends AbstractModel<String> {

	@Getter
	@Setter
	@Id
	@Column(name="placementTaskID")
	private String id;

	@Getter
	@Setter
	@Column(name="command", nullable=false)
	private String command;

	@Getter
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="placementTaskID", referencedColumnName="placementTaskID")
	private Set<PlacementSentence> sentences;
	
	public PlacementTask() {
		this.id = new UUID().toString();
	}
}