// (C) websystique

package main.model.user.userrole;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

import main.model.abstracts.AbstractUuidModel;

@Entity
@Table(name="userroles")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "roleID")) })
public class UserRole extends AbstractUuidModel {

	enum Role {
		USER,
		ADMIN
	}

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
