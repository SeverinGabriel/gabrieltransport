
package ch.gabrieltransport.auftragverwaltung.dal;

import com.xdev.dal.JPADAO;
import ch.gabrieltransport.auftragverwaltung.entities.Resource;

/**
 * Home object for domain model class Resource.
 * 
 * @see Resource
 */


public class ResourceDAO extends JPADAO<Resource, String> {
	public ResourceDAO() {
		super(Resource.class);
	}
}