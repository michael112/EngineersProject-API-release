package main.model.user.userprofile;

import javax.persistence.*;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.user.User;
import main.model.placementtest.PlacementTest;
import main.model.language.Language;

import main.model.AbstractModel;

@Entity
@Table(name="placementTestResults")
public class PlacementTestResult extends AbstractModel<String> {
	
	// ===== fields =====
	
	@Getter
	@Setter
	@Id
	@Column(name="placementTestResultID")
	private String id;
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
		this.id = new UUID().toString();
	}
	
}