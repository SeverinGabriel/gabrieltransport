
package ch.gabrieltransport.auftragverwaltung.dal;

import com.xdev.dal.JPADAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;

/**
 * Home object for domain model class Fahrzeug.
 * 
 * @see Fahrzeug
 */
public class FahrzeugDAO extends JPADAO<Fahrzeug, Integer> {
	public FahrzeugDAO() {
		super(Fahrzeug.class);
		
	}
}