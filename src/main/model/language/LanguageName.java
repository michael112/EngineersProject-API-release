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
import javax.persistence.Transient;
import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.Getter;
import lombok.Setter;

import main.model.abstracts.AbstractModel;

@Entity
@Table(name="languageNames")
@Access(AccessType.FIELD)
public class LanguageName extends AbstractModel<LanguageName.LanguageNameKey> {

	// ===== fields =====
	@Transient
	private LanguageNameKey key;

	@EmbeddedId
	@Access(AccessType.PROPERTY)
	@Override
	public LanguageNameKey getId() {
		return this.key;
	}
	@Override
	public void setId(LanguageNameKey key) {
		this.key = key;
	}

	@Getter
	@MapsId("namedLanguageID")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="namedLanguageID", referencedColumnName="languageID", nullable=false)
	private Language namedLanguage; //  język którego wpis dotyczy
	public void setNamedLanguage(Language namedLanguage) {
		this.namedLanguage = namedLanguage;
		this.key.setNamedLanguageID(namedLanguage.getId());
	}
	
	@Getter
	@MapsId("namingLanguageID")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="namingLanguageID", referencedColumnName="languageID", nullable=false)
	private Language namingLanguage; // język w którym robimy nazwę
	public void setNamingLanguage(Language namingLanguage) {
		this.namingLanguage = namingLanguage;
		this.key.setNamingLanguageID(namingLanguage.getId());
	}

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
	public LanguageName(Language namedLanguage, Language namingLanguage, String languageName) {
		this();
		this.setNamedLanguage(namedLanguage);
		this.setNamingLanguage(namingLanguage);
		this.setLanguageName(languageName);
	}
	public LanguageName(Language language, String languageName) {
		this(language, language, languageName);
	}

}