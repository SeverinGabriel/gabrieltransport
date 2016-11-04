package ch.gabrieltransport.auftragverwaltung.business;

import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugauftragDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;

public class BOFahrzeugAuftragService {

	private FahrzeugauftragDAO fahrzeugAuftragDAO = new FahrzeugauftragDAO();
	public void deleteFahrzeugAuftrag(Fahrzeugauftrag fahrzeugA){
		fahrzeugAuftragDAO.remove(fahrzeugA);
	}
	
	public void persistFahrzeugAuftrag(Fahrzeugauftrag fahrzeugA){
		fahrzeugAuftragDAO.persist(fahrzeugA);
	}
}
