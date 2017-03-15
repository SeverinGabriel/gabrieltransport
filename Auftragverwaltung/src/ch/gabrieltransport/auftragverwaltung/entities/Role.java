package ch.gabrieltransport.auftragverwaltung.entities;

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
import com.xdev.security.authorization.jpa.AuthorizationRole;
import java.util.Collection;
import java.util.Set;
import ch.gabrieltransport.auftragverwaltung.dal.RoleDAO;

@Entity
@DAO(daoClass = RoleDAO.class)
@Table(name = "`ROLE`")
public class Role implements AuthorizationRole {

	private Set<Role> parentRoles;
	private Set<Role> childRoles;
	private Set<Resource> resources;
	private String name;

	public Role() {
		super();
	}

	@Id
	@Column(name = "`NAME`", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ROLERESOURCENM", joinColumns = @JoinColumn(name = "`ROLE`", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "`RESOURCE`", nullable = false, updatable = false))
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ROLEROLENM", joinColumns = @JoinColumn(name = "PARENTROLE", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "CHILDROLE", nullable = false, updatable = false))
	public Set<Role> getChildRoles() {
		return childRoles;
	}

	public void setChildRoles(Set<Role> childRoles) {
		this.childRoles = childRoles;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ROLEROLENM", joinColumns = @JoinColumn(name = "CHILDROLE", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "PARENTROLE", nullable = false, updatable = false))
	public Set<Role> getParentRoles() {
		return parentRoles;
	}

	public void setParentRoles(Set<Role> parentRoles) {
		this.parentRoles = parentRoles;
	}

	@Override
	public String roleName() {
		return this.getName();
	}

	@Override
	public Collection<? extends AuthorizationResource> resources() {
		return this.getResources();
	}

	@Override
	public Collection<? extends AuthorizationRole> roles() {
		return this.getChildRoles();
	}

}
