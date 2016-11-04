package ch.gabrieltransport.auftragverwaltung.business;

import ch.gabrieltransport.auftragverwaltung.dal.FahrerauftragDAO;
import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugauftragDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerauftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;

public class BOFahrerAuftragService {

	private FahrerauftragDAO fahrerAuftragDAO = new FahrerauftragDAO();
	public void deleteFahrerAuftrag(Fahrerauftrag fahrzeugA){
		fahrerAuftragDAO.remove(fahrzeugA);
	}
}
