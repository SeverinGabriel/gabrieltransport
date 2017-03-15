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
import java.util.Set;
import ch.gabrieltransport.auftragverwaltung.dal.ResourceDAO;

@Entity
@DAO(daoClass = ResourceDAO.class)
@Table(name = "`RESOURCE`")
public class Resource implements AuthorizationResource {

	private String name;
	private Set<Role> roles;

	public Resource() {
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
	@JoinTable(name = "ROLERESOURCENM", joinColumns = @JoinColumn(name = "`RESOURCE`", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "`ROLE`", nullable = false, updatable = false))
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String resourceName() {
		return this.getName();
	}

}
