
package ch.gabrieltransport.auftragverwaltung.dal;

import com.xdev.dal.JPADAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerfunktionmap;

/**
 * Home object for domain model class Fahrerfunktionmap.
 * 
 * @see Fahrerfunktionmap
 */
public class FahrerfunktionmapDAO extends JPADAO<Fahrerfunktionmap, Integer> {
	public FahrerfunktionmapDAO() {
		super(Fahrerfunktionmap.class);
	}
}