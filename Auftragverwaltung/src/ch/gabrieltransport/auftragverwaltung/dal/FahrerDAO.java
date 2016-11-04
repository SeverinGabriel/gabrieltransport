
package ch.gabrieltransport.auftragverwaltung.dal;

import com.xdev.dal.JPADAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;

/**
 * Home object for domain model class Fahrer.
 * 
 * @see Fahrer
 */
public class FahrerDAO extends JPADAO<Fahrer, Integer> {
	public FahrerDAO() {
		super(Fahrer.class);
		
	}
}