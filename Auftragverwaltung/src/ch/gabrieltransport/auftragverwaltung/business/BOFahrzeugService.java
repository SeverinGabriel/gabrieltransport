package ch.gabrieltransport.auftragverwaltung.business;

import java.util.List;

import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;

public class BOFahrzeugService {

	private FahrzeugDAO vehicleDAO = new FahrzeugDAO();
	public void deleteVehicle(Fahrzeug vehicle){
		vehicleDAO.remove(vehicle);
	}
}
