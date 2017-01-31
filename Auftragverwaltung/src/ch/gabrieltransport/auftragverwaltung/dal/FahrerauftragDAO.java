
package ch.gabrieltransport.auftragverwaltung.dal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.Session;

import com.xdev.dal.JPADAO;

import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
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
	
	@SuppressWarnings("unchecked")
	public List<Fahrerauftrag> findAuftrageon(LocalDateTime date, Fahrer fahrer) {
		
		LocalTime midnight = LocalTime.MAX;
		LocalDate today = date.toLocalDate();
		LocalDateTime dateUntil = LocalDateTime.of(today, midnight);
		
		Session session = this.getSession(); 
		String hql = "Select fa FROM Fahrerauftrag fa " + 
				"where fa.fahrer = :fahrer and " + 
				"((:dateFrom BETWEEN fa.vonDatum AND fa.bisDatum OR :dateUntil BETWEEN fa.vonDatum AND fa.bisDatum) " +
				"OR (fa.vonDatum BETWEEN :dateFrom AND :dateUntil OR fa.bisDatum BETWEEN :dateFrom AND :dateUntil))"
				+ " ORDER BY fa.vonDatum";
		List<Fahrerauftrag> auftraege = session.createQuery(hql)
				.setParameter("fahrer", fahrer)
				.setParameter("dateFrom", date)
				.setParameter("dateUntil", dateUntil)
				.list();
		return auftraege;
	}
}