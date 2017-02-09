
package ch.gabrieltransport.auftragverwaltung.dal;

import com.xdev.dal.JPADAO;
import ch.gabrieltransport.auftragverwaltung.entities.FahrzeugFunktion;

/**
 * Home object for domain model class FahrzeugFunktion.
 * 
 * @see FahrzeugFunktion
 */
public class FahrzeugFunktionDAO extends JPADAO<FahrzeugFunktion, Integer> {
	public FahrzeugFunktionDAO() {
		super(FahrzeugFunktion.class);
	}
}