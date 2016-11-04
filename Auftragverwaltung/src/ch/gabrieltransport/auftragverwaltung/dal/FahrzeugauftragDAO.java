
package ch.gabrieltransport.auftragverwaltung.dal;

import com.xdev.dal.JPADAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;

/**
 * Home object for domain model class Fahrzeugauftrag.
 * 
 * @see Fahrzeugauftrag
 */
public class FahrzeugauftragDAO extends JPADAO<Fahrzeugauftrag, Integer> {
	public FahrzeugauftragDAO() {
		super(Fahrzeugauftrag.class);
	}
}