
package ch.gabrieltransport.auftragverwaltung.dal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.Session;

import com.xdev.dal.JPADAO;

import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
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
	
	@SuppressWarnings("unchecked")
	public List<Fahrzeugauftrag> findAuftrageon(LocalDateTime date, Fahrzeug fahrzeug) {
		
		LocalTime midnight = LocalTime.MAX;
		LocalDate today = date.toLocalDate();
		LocalDateTime dateUntil = LocalDateTime.of(today, midnight);
		
		Session session = this.getSession(); 
		String hql = "Select fa FROM Fahrzeugauftrag fa " + 
				"where fa.fahrzeug = :fahrzeug and " + 
				"((:dateFrom BETWEEN fa.vonDatum AND fa.bisDatum OR :dateUntil BETWEEN fa.vonDatum AND fa.bisDatum) " +
				"OR (fa.vonDatum BETWEEN :dateFrom AND :dateUntil OR fa.bisDatum BETWEEN :dateFrom AND :dateUntil))"
				+ " ORDER BY fa.vonDatum";
		List<Fahrzeugauftrag> auftraege = session.createQuery(hql)
				.setParameter("fahrzeug", fahrzeug)
				.setParameter("dateFrom",date)
				.setParameter("dateUntil", dateUntil)
				.list();		
		return auftraege;
	}
	
	@SuppressWarnings("unchecked")
	public List<Fahrzeugauftrag> findAuftrageon(LocalDateTime date, Anhaenger anhaenger) {
		
		LocalTime midnight = LocalTime.MAX;
		LocalDate today = date.toLocalDate();
		LocalDateTime dateUntil = LocalDateTime.of(today, midnight);
		
		Session session = this.getSession(); 
		String hql = "Select fa FROM Fahrzeugauftrag fa " + 
				"where fa.anhaenger = :anhaenger and " + 
				"((:dateFrom BETWEEN fa.vonDatum AND fa.bisDatum OR :dateUntil BETWEEN fa.vonDatum AND fa.bisDatum) " +
				"OR (fa.vonDatum BETWEEN :dateFrom AND :dateUntil OR fa.bisDatum BETWEEN :dateFrom AND :dateUntil))"
				+ " ORDER BY fa.vonDatum";
		List<Fahrzeugauftrag> auftraege = session.createQuery(hql)
				.setParameter("anhaenger", anhaenger)
				.setParameter("dateFrom",date)
				.setParameter("dateUntil", dateUntil)
				.list();		
		return auftraege;
	}
	
}