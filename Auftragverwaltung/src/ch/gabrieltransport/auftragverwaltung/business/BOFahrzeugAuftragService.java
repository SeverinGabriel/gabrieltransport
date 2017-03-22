package ch.gabrieltransport.auftragverwaltung.business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugauftragDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;

public class BOFahrzeugAuftragService {

	private FahrzeugauftragDAO fahrzeugAuftragDAO = new FahrzeugauftragDAO();
	public void deleteFahrzeugAuftrag(Fahrzeugauftrag fahrzeugA){
		fahrzeugAuftragDAO.remove(fahrzeugA);
	}
	
	public void persistFahrzeugAuftrag(Fahrzeugauftrag fahrzeugA){
		fahrzeugAuftragDAO.persist(fahrzeugA);
	}
	
	public void deleteAllFromAuftrag(Auftrag auftrag){
		for(Fahrzeugauftrag fzA: auftrag.getFahrzeugauftrags()){
			fahrzeugAuftragDAO.remove(fzA);
		}
	}
	
	public void deleteAllOfVehicle(Fahrzeug vehicle){
		List<Fahrzeugauftrag> tasksToDelete = fahrzeugAuftragDAO.findAllByVehicle(vehicle);
		for (Fahrzeugauftrag fahrzeugauftrag : tasksToDelete) {
			fahrzeugAuftragDAO.remove(fahrzeugauftrag);
		}
	}
}
