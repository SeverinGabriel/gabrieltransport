
package ch.gabrieltransport.auftragverwaltung.dal;

import java.util.List;

import org.hibernate.Session;

import com.xdev.dal.JPADAO;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaengertyp;

/**
 * Home object for domain model class Anhaenger.
 * 
 * @see Anhaenger
 */
public class AnhaengerDAO extends JPADAO<Anhaenger, Integer> {
	public AnhaengerDAO() {
		super(Anhaenger.class);
	}
	
	public List<Anhaenger> getAllbyTyp(Anhaengertyp typ){
		
		Session session = this.getSession(); 
		String hql = "Select a FROM Anhaenger a where anhaengertyp= :anhaengertyp";
		List<Anhaenger> trailer = session.createQuery(hql)
				.setParameter("anhaengertyp", typ)
				.list();
				
		return trailer;
	}
}