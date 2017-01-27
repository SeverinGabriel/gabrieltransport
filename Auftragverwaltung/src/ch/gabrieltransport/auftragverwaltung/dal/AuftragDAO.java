
package ch.gabrieltransport.auftragverwaltung.dal;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hibernate.Criteria;
import org.hibernate.Session;

import ch.gabrieltransport.auftragverwaltung.dal.*;

import com.xdev.dal.JPADAO;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;

/**
 * Home object for domain model class Auftrag.
 * 
 * @see Auftrag
 */
public class AuftragDAO extends JPADAO<Auftrag, Integer> {
	
	public AuftragDAO() {
		super(Auftrag.class);
		
	}
	@SuppressWarnings("unchecked")
	public List<Auftrag> findAuftrageon(LocalDateTime date, Fahrzeug fahrzeug) {
		
		LocalTime midnight = LocalTime.MAX;
		LocalDate today = date.toLocalDate();
		LocalDateTime dateUntil = LocalDateTime.of(today, midnight);
		
		Session session = this.getSession(); 
		String hql = "Select a FROM Fahrzeugauftrag fa inner join fa.auftrag a " + 
				"where fa.fahrzeug = :fahrzeug and " + 
				"((:dateFrom BETWEEN fa.vonDatum AND fa.bisDatum OR :dateUntil BETWEEN fa.vonDatum AND fa.bisDatum) " +
				"OR (fa.vonDatum BETWEEN :dateFrom AND :dateUntil OR fa.bisDatum BETWEEN :dateFrom AND :dateUntil))"
				+ " ORDER BY fa.vonDatum";
		List<Auftrag> auftraege = session.createQuery(hql)
				.setParameter("fahrzeug", fahrzeug)
				.setParameter("dateFrom",date)
				.setParameter("dateUntil", dateUntil)
				.list();
		
		
		
		
		//Query q = this.em().createQuery("SELECT a FROM Fahrzeugauftrag fa "
			//	+ "inner join fa.auftrag a "
			//	+ "where fa.fahrzeug = :fahrzeug and ((:dateFrom BETWEEN fa.vonDatum AND fa.bisDatum OR :dateUntil BETWEEN fa.vonDatum AND fa.bisDatum) OR (fa.vonDatum BETWEEN :dateFrom AND :dateUntil OR fa.bisDatum BETWEEN :dateFrom AND :dateUntil))");
			
		//q.setParameter("fahrzeug", fahrzeug);
		
		
		return auftraege;
	}


}