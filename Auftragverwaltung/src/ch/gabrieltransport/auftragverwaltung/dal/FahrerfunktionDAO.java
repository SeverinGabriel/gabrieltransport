
package ch.gabrieltransport.auftragverwaltung.dal;

import com.xdev.dal.JPADAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerfunktion;

/**
 * Home object for domain model class Fahrerfunktion.
 * 
 * @see Fahrerfunktion
 */
public class FahrerfunktionDAO extends JPADAO<Fahrerfunktion, Integer> {
	public FahrerfunktionDAO() {
		super(Fahrerfunktion.class);
	}
}