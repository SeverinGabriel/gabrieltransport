package ch.gabrieltransport.auftragverwaltung.business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;

import ch.gabrieltransport.auftragverwaltung.dal.AuftragDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerauftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;

public class BOAuftragService {

	private final AuftragDAO auftragDAO = new AuftragDAO();
	private final BOFahrerAuftragService fahrerAuftragService = new BOFahrerAuftragService();
	private final BOFahrzeugAuftragService fahrzeugAuftragService = new BOFahrzeugAuftragService();
	public final void saveBestellung(Auftrag auftrag) {
		auftragDAO.save(auftrag);
    }

    public final void deleteAuftrag(final Auftrag auftrag) {
	        auftragDAO.remove(auftrag);
	        
    }
    
    public final void persistAuftrag(final Auftrag auftrag, Fahrzeugauftrag fzA){
    		
    		auftragDAO.persist(auftrag);
    		fahrzeugAuftragService.persistFahrzeugAuftrag(fzA);
    }
    
    public List<Auftrag> findAuftrageon(LocalDateTime date, Fahrzeug fahrzeug){
    	List<Auftrag> auftraege = new ArrayList<Auftrag>();
    	try{
	    	
	    	auftraege = auftragDAO.findAuftrageon(date, fahrzeug);
	    	
    	}catch(Exception e){
			System.err.println(e.getMessage());
			auftragDAO.rollback();
    	}
    	return auftraege;
    }
}
