// (C) websystique

package main.model.user.userrole;

import com.eaio.uuid.UUID;

import lombok.Getter;
import lombok.Setter;

import main.model.AbstractModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Entity
@Table(name="userroles")
public class UserRole extends AbstractModel<String> {

	enum Role {
		USER,
		ADMIN
	}

	@Getter
	@Setter
	@Id
	@Column(name="roleID")
	private String id;

	@Column(name="role", length=15, unique=true, nullable=false)
	@Enumerated(EnumType.STRING)
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

	public UserRole() {
		this.id = new UUID().toString();
	}

}
