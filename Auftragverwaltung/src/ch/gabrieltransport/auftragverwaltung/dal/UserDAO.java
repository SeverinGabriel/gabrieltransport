
package ch.gabrieltransport.auftragverwaltung.dal;

import com.xdev.dal.JPADAO;
import ch.gabrieltransport.auftragverwaltung.entities.User;

/**
 * Home object for domain model class User.
 * 
 * @see User
 */
public class UserDAO extends JPADAO<User, Integer> {
	public UserDAO() {
		super(User.class);
	}
}