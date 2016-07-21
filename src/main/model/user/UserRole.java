// (C) websystique

package main.model.user;

import com.eaio.uuid.UUID;

import main.model.AbstractModel;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name="user_roles")
public class UserRole extends AbstractModel<String> {

	enum Role {
		USER,
		ADMIN
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="roleID")
	private String id;

	@Column(name="role", length=15, unique=true, nullable=false)
	@Enumerated(EnumType.STRING)
	private Role role;

	//@Override
	public String getId() {
		return id;
	}

	//@Override
	public void setId(String id) {
		this.id = id;
	}

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
