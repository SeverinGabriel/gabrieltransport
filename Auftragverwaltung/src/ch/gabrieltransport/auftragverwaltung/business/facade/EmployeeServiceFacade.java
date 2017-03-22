package ch.gabrieltransport.auftragverwaltung.business.facade;

import javax.transaction.Transactional;

import ch.gabrieltransport.auftragverwaltung.business.BOFahrerAuftragService;
import ch.gabrieltransport.auftragverwaltung.business.BOFahrerService;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;

public class EmployeeServiceFacade {

	private BOFahrerAuftragService boDriverTaskService = new BOFahrerAuftragService();
	private BOFahrerService boDriverService = new BOFahrerService();
	
	@Transactional
	public void deleteEmployee(Fahrer employee){
		boDriverTaskService.deleteAllOfEmployee(employee);
		boDriverService.deleteEmployee(employee);
	}
	
}
