package main.model.user.userprofile;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="testID", referencedColumnName="placementTestID", nullable=false)
	private PlacementTest test;
	public void setTest(PlacementTest test) {
		if( test != null ) {
			if (this.test != null) {
				if (this.test.containsResult(this)) {
					this.test.removeResult(this);
				}
			}
			this.test = test;
			test.addResult(this); // przypisanie powiązania
		}
		else throw new IllegalArgumentException();
	}

	@Getter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userID", referencedColumnName="userID", nullable=false)
	private User user;
	public void setUser(User user) {
		if( user != null ) {
			if (this.user != null) {
				if (this.user.containsPlacementTest(this)) {
					this.user.removePlacementTest(this);
				}
			}
			this.user = user;
			if (user != null) user.addPlacementTest(this); // przypisanie powiązania
		}
		else throw new IllegalArgumentException();
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

	public PlacementTestResult(PlacementTest test, User user) {
		this();
		this.setTest(test);
		this.setUser(user);
	}

	public PlacementTestResult(PlacementTest test, User user, double result) {
		this(test, user);
		this.setResult(result);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object otherObj) {
		return super.equals(otherObj);
	}

}