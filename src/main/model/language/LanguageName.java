// Sprawidzić działanie klucza głównego

package main.model.language;

import java.io.Serializable;

import javax.persistence.Entity;
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

import main.model.ModelInterface;

@Entity
@Table(name="languageNames")
public class LanguageName implements ModelInterface<LanguageName.LanguageNameKey> {

	// ===== fields =====

	@EmbeddedId
	private LanguageNameKey languageNameKey;

	@Override
	public LanguageNameKey getId() {
		return this.languageNameKey;
	}

	@Override
	public void setId(LanguageNameKey key) {
		this.languageNameKey = key;
	}

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
	public static class LanguageNameKey implements Serializable {
		@Column(name="namingLanguageID", nullable=false)
		@Getter
		@Setter
		private String namingLanguageID;

		@Column(name="namedLanguageID", nullable=false)
		@Getter
		@Setter
		private String namedLanguageID;



		public LanguageNameKey() {}

		@Override
		public int hashCode() {
			return (namingLanguageID + namedLanguageID).hashCode();
		}
		@Override
		public boolean equals(Object obj) {
			if( obj == null ) return false;
			if( !(obj instanceof LanguageNameKey)) return false;
			LanguageNameKey lnk = (LanguageNameKey)obj;
			return ( ( lnk.getNamedLanguageID().equals(this.getNamedLanguageID()) ) && ( lnk.getNamingLanguageID().equals(this.getNamingLanguageID()) ) );
		}
	}

	public LanguageName() {
		this.setId(new LanguageNameKey());
	}

	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}

}