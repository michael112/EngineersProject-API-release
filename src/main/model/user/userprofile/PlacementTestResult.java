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
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "placementTestResultID")) })
public class PlacementTestResult extends AbstractUuidModel {

	@Getter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="testID", referencedColumnName="placementTestID", nullable=false)
	private PlacementTest test;
	public void setTest(PlacementTest test) {
		// do sprawdzenia
		if( this.test != null ) {
			if (this.test.containsResult(this)) {
				this.test.removeResult(this);
			}
		}
		this.test = test;
		test.addResult(this); // przypisanie powiązania
	}

	@Getter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userID", referencedColumnName="userID", nullable=false)
	private User user;
	public void setUser(User user) {
		// do sprawdzenia
		if( this.user != null ) {
			if (this.user.containsPlacementTest(this)) {
				this.user.removePlacementTest(this);
			}
		}
		this.user = user;
		user.addPlacementTest(this); // przypisanie powiązania
	}

	@Getter
	@Setter
	@Column(name="result", nullable=false)
	private double result;
	
	public Language getLanguage() {
		return this.test.getLanguage();
	}


	
	public PlacementTestResult() {
		super();
	}
	
}