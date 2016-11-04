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
import com.xdev.security.authorization.jpa.AuthorizationResource;
import com.xdev.security.authorization.jpa.AuthorizationRole;
import com.xdev.util.Caption;

import ch.gabrieltransport.auftragverwaltung.dal.RoleDAO;

/**
 * Role
 */
@DAO(daoClass = RoleDAO.class)
@Caption("{%name}")
@Entity
@Table(name = "role", catalog = "gabrieltransport")
public class Role implements java.io.Serializable, AuthorizationRole {

	private String name;
	private Set<Role> rolesForParentrole = new HashSet<Role>(0);
	private Set<Role> rolesForChildrole = new HashSet<Role>(0);
	private Set<Resource> resources = new HashSet<Resource>(0);
	private Set<User> users = new HashSet<User>(0);

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public Role(String name, Set<Role> rolesForParentrole, Set<Role> rolesForChildrole, Set<Resource> resources,
			Set<User> users) {
		this.name = name;
		this.rolesForParentrole = rolesForParentrole;
		this.rolesForChildrole = rolesForChildrole;
		this.resources = resources;
		this.users = users;
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

	@Caption("RolesForParentrole")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rolerolenm", catalog = "gabrieltransport", joinColumns = {
			@JoinColumn(name = "childrole", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "parentrole", nullable = false, updatable = false) })
	public Set<Role> getRolesForParentrole() {
		return this.rolesForParentrole;
	}

	public void setRolesForParentrole(Set<Role> rolesForParentrole) {
		this.rolesForParentrole = rolesForParentrole;
	}

	@Caption("RolesForChildrole")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rolerolenm", catalog = "gabrieltransport", joinColumns = {
			@JoinColumn(name = "parentrole", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "childrole", nullable = false, updatable = false) })
	public Set<Role> getRolesForChildrole() {
		return this.rolesForChildrole;
	}

	public void setRolesForChildrole(Set<Role> rolesForChildrole) {
		this.rolesForChildrole = rolesForChildrole;
	}

	@Caption("Resources")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "roleresourcenm", catalog = "gabrieltransport", joinColumns = {
			@JoinColumn(name = "role", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "resource", nullable = false, updatable = false) })
	public Set<Resource> getResources() {
		return this.resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@Caption("Users")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "userrolenm", catalog = "gabrieltransport", joinColumns = {
			@JoinColumn(name = "role", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "user", nullable = false, updatable = false) })
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
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
		return this.getRolesForParentrole();
	}

}
