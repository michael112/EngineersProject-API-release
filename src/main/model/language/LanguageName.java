// Sprawidzić działanie klucza głównego

package main.model.language;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.MapsId;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;

import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(LanguageName.LanguageNameKey.class)
@Table(name="languageNames")
public class LanguageName {

	// ===== fields =====

	@EmbeddedId
	private LanguageNameKey languageNameKey;

	@Getter
	@Setter
	@MapsId("namedLanguageID")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="namedLanguageID", referencedColumnName="languageID", nullable=false)
	private Language namedLanguage; //  język którego wpis dotyczy
	
	@Getter
	@Setter
	@MapsId("namingLanguageID")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="namingLanguageID", referencedColumnName="languageID", nullable=false)
	private Language namingLanguage; // język w którym robimy nazwę

	// double primary key: PRIMARY KEY (namedLanguageID, namingLanguageID)

	@Getter
	@Setter
	@Column(name="languageName", nullable=false)
	private String languageName;

	@Embeddable
	class LanguageNameKey {
		@Column(name="namingLanguageID", nullable=false)
		private String namingLanguageID;

		@Column(name="namedLanguageID", nullable=false)
		private String namedLanguageID;
	}

}