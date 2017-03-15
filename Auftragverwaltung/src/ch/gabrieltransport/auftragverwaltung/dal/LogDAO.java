
package ch.gabrieltransport.auftragverwaltung.dal;

import ch.gabrieltransport.auftragverwaltung.entities.Log;
import com.xdev.dal.JPADAO;

/**
 * Home object for domain model class Log.
 * 
 * @see Log
 */
public class LogDAO extends JPADAO<Log, Integer> {
	public LogDAO() {
		super(Log.class);
	}
}