package ch.gabrieltransport.auftragverwaltung.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import ch.gabrieltransport.auftragverwaltung.dal.UserDAO;
import com.xdev.dal.DAO;
import com.xdev.security.authentication.CredentialsUsernamePassword;
import com.xdev.security.authorization.jpa.AuthorizationRole;
import com.xdev.security.authorization.jpa.AuthorizationSubject;
import com.xdev.util.Caption;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User
 */
@DAO(daoClass = UserDAO.class)
@Caption("{%username}")
@Entity
@Table(name = "user", catalog = "gabrieltransport")
public class User implements java.io.Serializable, AuthorizationSubject, CredentialsUsernamePassword{

	private int iduser;
	private byte[] password;
	private String username;
	private Set<Role> roles = new HashSet<Role>(0);
	private Set<Log> logs = new HashSet<Log>(0);

	public User() {
	}

	public User(String username, byte[] password) {
		this.username = username;
		this.password = password;
	}

	public User(String username, byte[] password, Set<Role> roles, Set<Log> logs) {
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.logs = logs;
	}

	@Caption("Iduser")
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "iduser", unique = true, nullable = false, columnDefinition = "INT")
	public int getIduser() {
		return this.iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	@Caption("Password")
	@Column(name = "password", nullable = false, columnDefinition = "TINYBLOB")
	public byte[] getPassword() {
		return this.password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	@Caption("Username")
	@Column(name = "username", unique = true, nullable = false, columnDefinition = "VARCHAR")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Caption("Logs")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Log> getLogs() {
		return this.logs;
	}

	public void setLogs(Set<Log> logs) {
		this.logs = logs;
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
