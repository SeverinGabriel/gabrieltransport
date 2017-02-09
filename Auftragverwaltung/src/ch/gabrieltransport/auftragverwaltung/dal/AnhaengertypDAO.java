
package ch.gabrieltransport.auftragverwaltung.dal;

import ch.gabrieltransport.auftragverwaltung.entities.Anhaengertyp;
import com.xdev.dal.JPADAO;

/**
 * Home object for domain model class Anhaengertyp.
 * 
 * @see Anhaengertyp
 */
public class AnhaengertypDAO extends JPADAO<Anhaengertyp, Integer> {
	public AnhaengertypDAO() {
		super(Anhaengertyp.class);
	}
}