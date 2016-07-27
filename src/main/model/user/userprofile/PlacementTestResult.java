package main.model.user.userprofile;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;
import main.model.placementtest.PlacementTest;
import main.model.language.Language;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="placementTestResults")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "placementTestResultID")) })
public class PlacementTestResult extends AbstractUuidModel {

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="testID", referencedColumnName="placementTestID", nullable=false)
	private PlacementTest test;
	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userID", referencedColumnName="userID", nullable=false)
	private User user;
	@Getter
	@Setter
	@Column(name="result", nullable=false)
	private double result;
	
	public Language getLanguage() {
		return this.test.getLanguage();
	}
	
	// ===== constructor ======
	
	public PlacementTestResult() {
		super();
	}
	
}