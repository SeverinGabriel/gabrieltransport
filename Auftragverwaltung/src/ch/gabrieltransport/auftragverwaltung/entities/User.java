package ch.gabrieltransport.auftragverwaltung.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.xdev.dal.DAO;
import com.xdev.security.authentication.CredentialsUsernamePassword;
import com.xdev.security.authorization.jpa.AuthorizationRole;
import com.xdev.security.authorization.jpa.AuthorizationSubject;
import com.xdev.util.Caption;

import ch.gabrieltransport.auftragverwaltung.dal.UserDAO;

/**
 * User
 */
@DAO(daoClass = UserDAO.class)
@Caption("{%username}")
@Entity
@Table(name = "user", catalog = "gabrieltransport")
public class User implements java.io.Serializable, AuthorizationSubject, CredentialsUsernamePassword {

	private String username;
	private byte[] password;
	private Set<Role> roles = new HashSet<Role>(0);

	public User() {
	}

	public User(String username, byte[] password) {
		this.username = username;
		this.password = password;
	}

	public User(String username, byte[] password, Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	@Caption("Username")
	@Id

	@Column(name = "username", unique = true, nullable = false)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Caption("Password")
	@Column(name = "password", nullable = false)
	public byte[] getPassword() {
		return this.password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	@Caption("Roles")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "userrolenm", catalog = "gabrieltransport", joinColumns = {
			@JoinColumn(name = "user", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role", nullable = false, updatable = false) })
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String subjectName() {
		return this.getUsername();
	}

	@Override
	public Collection<? extends AuthorizationRole> roles() {
		return this.getRoles();
	}

	@Override
	public String username() {
		return this.getUsername();
	}

	@Override
	public byte[] password() {
		return this.getPassword();
	}

}
