package ch.gabrieltransport.auftragverwaltung.entities;

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
import com.xdev.security.authorization.jpa.AuthorizationResource;
import com.xdev.util.Caption;

import ch.gabrieltransport.auftragverwaltung.dal.ResourceDAO;

/**
 * Resource
 */
@DAO(daoClass = ResourceDAO.class)
@Caption("{%name}")
@Entity
@Table(name = "resource", catalog = "gabrieltransport")
public class Resource implements java.io.Serializable, AuthorizationResource {

	private String name;
	private Set<Role> roles = new HashSet<Role>(0);

	public Resource() {
	}

	public Resource(String name) {
		this.name = name;
	}

	public Resource(String name, Set<Role> roles) {
		this.name = name;
		this.roles = roles;
	}

	@Caption("Name")
	@Id

	@Column(name = "name", unique = true, nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Caption("Roles")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "roleresourcenm", catalog = "gabrieltransport", joinColumns = {
			@JoinColumn(name = "resource", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role", nullable = false, updatable = false) })
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String resourceName() {
		return this.getName();
	}

}
