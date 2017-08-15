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

	public void setId(String namingLanguageID, String namedLanguageID) {
		this.setId(new LanguageNameKey(namingLanguageID, namedLanguageID));
	}

	@Getter
	@MapsId("namedLanguageID")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="namedLanguageID", referencedColumnName="languageID", nullable=false)
	private Language namedLanguage; //  język którego wpis dotyczy
	public void setNamedLanguage(Language namedLanguage) {
		if( namedLanguage != null ) {
			if (this.namedLanguage != null) {
				if (this.namedLanguage.containsLanguageName(this)) {
					this.namedLanguage.removeLanguageName(this);
				}
			}
			this.namedLanguage = namedLanguage;
			this.key.setNamedLanguageID(namedLanguage.getId());
			namedLanguage.addLanguageName(this); // przypisanie powiązania
		}
		else throw new IllegalArgumentException();
	}
	
	@Getter
	@MapsId("namingLanguageID")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="namingLanguageID", referencedColumnName="languageID", nullable=false)
	private Language namingLanguage; // język w którym robimy nazwę
	public void setNamingLanguage(Language namingLanguage) {
		if( namingLanguage != null ) {
			this.namingLanguage = namingLanguage;
			this.key.setNamingLanguageID(namingLanguage.getId());
		}
		else throw new IllegalArgumentException();
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

		public LanguageNameKey(String namingLanguageID, String namedLanguageID) {
			this();
			this.setNamingLanguageID(namingLanguageID);
			this.setNamedLanguageID(namedLanguageID);
		}

		@Override
		public int hashCode() {
			return (namingLanguageID + namedLanguageID).hashCode();
		}
		@Override
		public boolean equals(Object obj) {
			if( obj == null ) return false;
			if( !( obj instanceof LanguageNameKey ) ) return false;
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

	@Override
	public int hashCode() {
		if( this.getId() != null ) return this.getId().hashCode();
		else return new Integer(0).hashCode();
	}

	@Override
	public boolean equals(Object otherObj) {
		return super.equals(otherObj);
	}

}