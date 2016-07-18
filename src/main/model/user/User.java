// (C) websystique

package main.model.user;

import main.model.AbstractModel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
public class User extends AbstractModel<Integer> {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="userID")
    @Getter
    @Setter
    private Integer id;

    @Column(name="username", unique=true, nullable=false)
    @Getter
    @Setter
    private String username;

    @Column(name="password", nullable=false)
    @Getter
    @Setter
    private String password;

    @Column(name="email", nullable=false)
    @Getter
    @Setter
    private String email;

    @Column(name="active", nullable=false)
    @Type(type="org.hibernate.type.NumericBooleanType")
    @Getter
    @Setter
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_user_roles",
            joinColumns = { @JoinColumn(name = "userID") },
            inverseJoinColumns = { @JoinColumn(name = "userRoleID") })
    @Getter
    @Setter
    private Set<UserRole> userRoles = new HashSet<UserRole>();

    @Column(name="firstname", nullable=false)
    @Getter
    @Setter
    private String firstName;

    @Column(name="lastname", nullable=false)
    @Getter
    @Setter
    private String lastName;

}
