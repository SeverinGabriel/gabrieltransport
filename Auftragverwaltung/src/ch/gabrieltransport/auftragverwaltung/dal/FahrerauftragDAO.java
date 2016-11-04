
package ch.gabrieltransport.auftragverwaltung.dal;

import com.xdev.dal.JPADAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerauftrag;

/**
 * Home object for domain model class Fahrerauftrag.
 * 
 * @see Fahrerauftrag
 */
public class FahrerauftragDAO extends JPADAO<Fahrerauftrag, Integer> {
	public FahrerauftragDAO() {
		super(Fahrerauftrag.class);
		
	}
}