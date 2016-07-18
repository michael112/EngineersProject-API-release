// (C) websystique

package main.model.user;

import main.model.AbstractModel;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name="user_roles")
public class UserRole extends AbstractModel<Integer> {

	enum Role {
		USER,
		ADMIN
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="roleID")
	private Integer id;

	@Column(name="role", length=15, unique=true, nullable=false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
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

}
