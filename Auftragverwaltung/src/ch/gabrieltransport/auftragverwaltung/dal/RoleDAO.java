package ch.gabrieltransport.auftragverwaltung.dal;

import com.xdev.dal.JPADAO;

import ch.gabrieltransport.auftragverwaltung.entities.Role;

public class RoleDAO extends JPADAO<Role, String> {

	public RoleDAO() {
		super(Role.class);
	}

}
