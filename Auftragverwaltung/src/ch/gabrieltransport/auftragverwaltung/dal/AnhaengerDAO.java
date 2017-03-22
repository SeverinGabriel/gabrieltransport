
package ch.gabrieltransport.auftragverwaltung.dal;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.hibernate.Session;

import com.xdev.dal.JPADAO;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger_;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaengertyp;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug_;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag_;

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


	public List<Anhaenger> findByTimeSpanAndType(LocalDateTime fromDate, LocalDateTime untilDate, Anhaengertyp type){
	
		String SQL = "Select an from Anhaenger where an not in SELECT a FROM Fahrzeugauftrag fa inner join Anhaenger a on fa.fkAnhänger = a.idanhaenger" + 
		"((:dateFrom BETWEEN fa.vonDatum AND fa.bisDatum OR :dateUntil BETWEEN fa.vonDatum AND fa.bisDatum) " +
		"OR (fa.vonDatum BETWEEN :dateFrom AND :dateUntil OR fa.bisDatum BETWEEN :dateFrom AND :dateUntil))";
		Query nativeQuery = em().createNativeQuery(SQL, Anhaenger.class);
		List<Anhaenger> resultList = nativeQuery.getResultList();
     
    return resultList;
	}
	
	
	/**
	 * @queryMethod Do not edit, method is generated by editor!
	 */
	public List<Anhaenger> findByTimeSpanAndType(LocalDateTime fromDate, LocalDateTime untilDate) {
		EntityManager entityManager = em();
	
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	
		ParameterExpression<LocalDateTime> fromDateParameter = criteriaBuilder.parameter(LocalDateTime.class, "fromDate");
		ParameterExpression<LocalDateTime> untilDateParameter = criteriaBuilder.parameter(LocalDateTime.class, "untilDate");
	
		CriteriaQuery<Anhaenger> criteriaQuery = criteriaBuilder.createQuery(Anhaenger.class);
	
		Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
	
		Root<Fahrzeugauftrag> root_fa = subquery.from(Fahrzeugauftrag.class);
		root_fa.alias("fa");
	
		subquery.select(criteriaBuilder.count(root_fa.get(Fahrzeugauftrag_.idfahrzeugauftrag)));
	
		subquery.where(criteriaBuilder.and(
				criteriaBuilder.or(
						criteriaBuilder.or(
								criteriaBuilder.between(fromDateParameter, root_fa.get(Fahrzeugauftrag_.vonDatum),
										root_fa.get(Fahrzeugauftrag_.bisDatum)),
								criteriaBuilder.between(fromDateParameter, root_fa.get(Fahrzeugauftrag_.vonDatum),
										root_fa.get(Fahrzeugauftrag_.bisDatum))),
						criteriaBuilder.or(criteriaBuilder.between(root_fa.get(Fahrzeugauftrag_.vonDatum),
								fromDateParameter, untilDateParameter),
								criteriaBuilder.between(root_fa.get(Fahrzeugauftrag_.bisDatum), fromDateParameter,
										untilDateParameter))),
				criteriaBuilder.equal(root_fa.get(Fahrzeugauftrag_.fahrzeug).get(Fahrzeug_.anhaenger), null)));
	
		Root<Anhaenger> root_a = criteriaQuery.from(Anhaenger.class);
		root_a.alias("a");
	
		criteriaQuery.where(criteriaBuilder.equal(criteriaBuilder.literal(0), subquery));
	
		TypedQuery<Anhaenger> query = entityManager.createQuery(criteriaQuery);
		query.setParameter(fromDateParameter, fromDate);
		query.setParameter(untilDateParameter, untilDate);
		return query.getResultList();
	}

	/**
	 * @queryMethod Do not edit, method is generated by editor!
	 */
	public List<Fahrzeugauftrag> findVehicleTasks(LocalDateTime fromDate, LocalDateTime untilDate) {
		EntityManager entityManager = em();
	
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	
		ParameterExpression<LocalDateTime> fromDateParameter = criteriaBuilder.parameter(LocalDateTime.class, "fromDate");
		ParameterExpression<LocalDateTime> untilDateParameter = criteriaBuilder.parameter(LocalDateTime.class, "untilDate");
	
		CriteriaQuery<Fahrzeugauftrag> criteriaQuery = criteriaBuilder.createQuery(Fahrzeugauftrag.class);
	
		Root<Fahrzeugauftrag> root_fa = criteriaQuery.from(Fahrzeugauftrag.class);
		root_fa.alias("fa");
	
		criteriaQuery.where(criteriaBuilder.or(
				criteriaBuilder.or(
						criteriaBuilder.between(fromDateParameter, root_fa.get(Fahrzeugauftrag_.vonDatum),
								root_fa.get(Fahrzeugauftrag_.bisDatum)),
						criteriaBuilder.between(fromDateParameter, root_fa.get(Fahrzeugauftrag_.vonDatum),
								root_fa.get(Fahrzeugauftrag_.bisDatum))),
				criteriaBuilder.or(
						criteriaBuilder.between(root_fa.get(Fahrzeugauftrag_.vonDatum), fromDateParameter,
								untilDateParameter),
						criteriaBuilder.between(root_fa.get(Fahrzeugauftrag_.bisDatum), fromDateParameter,
								untilDateParameter))));
	
		TypedQuery<Fahrzeugauftrag> query = entityManager.createQuery(criteriaQuery);
		query.setParameter(fromDateParameter, fromDate);
		query.setParameter(untilDateParameter, untilDate);
		return query.getResultList();
	}
}