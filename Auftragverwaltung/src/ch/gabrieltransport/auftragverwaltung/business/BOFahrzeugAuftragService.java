package ch.gabrieltransport.auftragverwaltung.business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugauftragDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;

public class BOFahrzeugAuftragService {

	private FahrzeugauftragDAO fahrzeugAuftragDAO = new FahrzeugauftragDAO();
	public void deleteFahrzeugAuftrag(Fahrzeugauftrag fahrzeugA){
		fahrzeugAuftragDAO.remove(fahrzeugA);
	}
	
	public void persistFahrzeugAuftrag(Fahrzeugauftrag fahrzeugA){
		fahrzeugAuftragDAO.persist(fahrzeugA);
	}
	
	/*public List<Fahrzeugauftrag> findFahrzeugAuftrageon(LocalDateTime date, Fahrzeug fahrzeug){
    	List<Fahrzeugauftrag> auftraege = new ArrayList<Fahrzeugauftrag>();
    	try{
	    	
	    	auftraege = fahrzeugAuftragDAO.findAuftrageon(date, fahrzeug);
	    	
    	}catch(Exception e){
			System.err.println(e.getMessage());
			fahrzeugAuftragDAO.rollback();
    	}
    	return auftraege;
    }*/
}
