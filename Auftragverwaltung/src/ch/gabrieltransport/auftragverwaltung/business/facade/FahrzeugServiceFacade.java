package ch.gabrieltransport.auftragverwaltung.business.facade;

import javax.transaction.Transactional;

import ch.gabrieltransport.auftragverwaltung.business.BOFahrzeugAuftragService;
import ch.gabrieltransport.auftragverwaltung.business.BOFahrzeugService;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;

public class FahrzeugServiceFacade {

	private BOFahrzeugService boFahrzeug = new BOFahrzeugService();
	private BOFahrzeugAuftragService boFahrzeugAuftrag = new BOFahrzeugAuftragService();
	
	@Transactional
	public void deleteVehicle(Fahrzeug vehicle){
		boFahrzeugAuftrag.deleteAllOfVehicle(vehicle);
		boFahrzeug.deleteVehicle(vehicle);
	}
}
