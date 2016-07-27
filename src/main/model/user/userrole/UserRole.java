// (C) websystique

package main.model.user.userrole;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
/*
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
*/
import javax.persistence.Transient;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="userroles")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "roleID")) })
public class UserRole extends AbstractUuidModel {

	enum Role {
		USER,
		ADMIN
	}

	/*
	@Column(name="role", length=30, columnDefinition = "varchar(30) default 'USER'", unique=true, nullable=false)
	@Enumerated(EnumType.STRING)
	@Getter
	@Setter
	private Role role;

	public String getRole() {
		return role.name();
	}

	public void setRole(String role) {
		try {
			this.role = Role.valueOf(role);
		}
		catch( IllegalArgumentException ex ) {
			this.role = Role.USER; // default value
		}
	}
	*/

	@Transient
	private Role role;

	@Access(AccessType.PROPERTY)
	@Column(name="role", length=30, unique=true, nullable=false)
	public String getRoleName() {
		return role.name();
	}

	public void setRoleName(String roleName) {
		try {
			this.role = Role.valueOf(roleName);
		}
		catch( IllegalArgumentException ex ) {
			this.role = Role.USER; // default value
		}
	}

	public UserRole() {
		super();
	}

}
