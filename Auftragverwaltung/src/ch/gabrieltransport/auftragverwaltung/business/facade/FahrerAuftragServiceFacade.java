package ch.gabrieltransport.auftragverwaltung.business.facade;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import ch.gabrieltransport.auftragverwaltung.business.BOLogService;
import ch.gabrieltransport.auftragverwaltung.dal.AuftragDAO;
import ch.gabrieltransport.auftragverwaltung.dal.FahrerauftragDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerauftrag;

public class FahrerAuftragServiceFacade {
	
	private FahrerauftragDAO fahrerAuftragDAO = new FahrerauftragDAO();
	private AuftragDAO auftragDAO = new AuftragDAO();
	private BOLogService boLogService = new BOLogService();

	@Transactional
	public Fahrerauftrag persistHoliday(LocalDateTime start, LocalDateTime end, Fahrer driver){
		Auftrag a = auftragDAO.find(1);
		if (a != null){
			Fahrerauftrag fa = new Fahrerauftrag(a, driver, start, end);
			fa.setFerien(true);
			fahrerAuftragDAO.persist(fa);
			boLogService.logMessage("Ferien von " + driver.getVorname() + " " + driver.getNachname() + "erfasst");
			return fa;
		}
		return null;
	}
	
	@Transactional
	public void removeTask(Fahrerauftrag fa){
		try{
			fahrerAuftragDAO.remove(fa);
			fahrerAuftragDAO.flush();
			boLogService.logMessage(fa.getFahrer().getVorname() + " " +  fa.getFahrer().getNachname()+ " von Auftrag " + fa.getAuftrag().getBezeichung()+  " entfernt");
		}catch(Exception e){
			System.err.println(e.getMessage());
			
		}
		
	}
}
