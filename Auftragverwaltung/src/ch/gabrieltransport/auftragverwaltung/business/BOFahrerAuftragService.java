package ch.gabrieltransport.auftragverwaltung.business;

import ch.gabrieltransport.auftragverwaltung.dal.FahrerauftragDAO;
import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugauftragDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerauftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;

public class BOFahrerAuftragService {

	private FahrerauftragDAO fahrerAuftragDAO = new FahrerauftragDAO();
	public void deleteFahrerAuftrag(Fahrerauftrag driverTask){
		fahrerAuftragDAO.remove(driverTask);
	}
	
	public void deleteAllFromTask(Auftrag task){
		fahrerAuftragDAO.deleteAuftragbyID(task.getIdAuftrag());
	}
	
	public void persistFahrerAuftrag(Fahrerauftrag driverTask){
		fahrerAuftragDAO.persist(driverTask);
	}
}
