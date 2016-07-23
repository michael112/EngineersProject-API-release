package main.model.placementtest;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.user.userprofile.PlacementTestResult;
import main.model.language.Language;
import main.model.AbstractModel;

@Entity
@Table(name="placementTests")
public class PlacementTest extends AbstractModel<String> {
	
	@Getter
	@Setter
	@Id
	@Column(name="placementTestID")
	private String id;
	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="languageID", referencedColumnName="languageID", nullable=false)
	private Language language;
	@Getter
	@Setter
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="placementTestID", referencedColumnName="placementTestID", nullable=false)
	private Set<PlacementTask> tasks;
	@Getter
	@Setter
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="test")
	private Set<PlacementTestResult> results;

	public PlacementTest() {
		this.id = new UUID().toString();
	}

}