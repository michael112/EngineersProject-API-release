package main.model.course;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.language.Language;

import main.model.AbstractModel;

@Entity
@Table(name="courseTypeNames")
public class CourseTypeName extends AbstractModel<String> {
	
	// ===== fields =====
	@Getter
	@Setter
	@Id
	@Column(name="courseTypeID")
	private String id; // nie wiem, czy potrzebne w tym wypadku

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseTypeID", referencedColumnName="courseTypeID", nullable=false)
	private CourseType courseType;

	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="namingLanguageID", referencedColumnName="languageID", nullable=false)
	private Language namingLanguage;

	@Getter
	@Setter
	@Column(name="courseTypeName", nullable=false)
	private String courseTypeName;

	public CourseTypeName() {
		this.id = new UUID().toString();
	}

}