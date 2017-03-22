package ch.gabrieltransport.auftragverwaltung.business.facade;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import com.vaadin.ui.Notification;

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

	/**
	 * Persist holiday for personal
	 * @param start LocalDateTime of holiday start
	 * @param end LocalDateTime of holiday end
	 * @param driver employee for which the holiday is persisted 
	 * @param type type of absence - defaults are Ferien, Militär, Kompensation, Krank but can be any String
	 * @return Persisted Fahrerauftrag
	 */
	@Transactional
	public Fahrerauftrag persistHoliday(LocalDateTime start, LocalDateTime end, Fahrer driver, String type){
		//Get task that collects all absences of type "type"
		List<Auftrag> a = auftragDAO.findByBezeichnung(type);
		Auftrag task;
		if (a.isEmpty()){
			//Never used keyword before. Persist new task
			task = new Auftrag(type);
			auftragDAO.persist(task);
		}
		else{
			task = a.get(0);
		}
		if (task != null){
			//Save new employeeTask
			Fahrerauftrag fa = new Fahrerauftrag(task, driver, start, end);
			fa.setFerien(true);
			fahrerAuftragDAO.persist(fa);
			boLogService.logMessage(type + " von " + driver.getVorname() + " " + driver.getNachname() + "erfasst");
			return fa;
		}
		return null;
	}
	
	/**
	 * Remove single Employeetask. Message is displayed if task has no employee left
	 * @param fa task to remove
	 */
	@Transactional
	public void removeTask(Fahrerauftrag fa){
		try{
			fahrerAuftragDAO.remove(fa);
			fahrerAuftragDAO.flush();
			boLogService.logMessage(fa.getFahrer().getVorname() + " " +  fa.getFahrer().getNachname()+ " von Auftrag " + fa.getAuftrag().getBezeichung()+  " entfernt");
			if(!fa.getFerien()){
				int countDriver = fa.getAuftrag().getFahrerauftrags().size();
				if(countDriver <= 1){
					Notification.show("Achtung der Auftrag " + fa.getAuftrag().getBezeichung() + " hat nun kein Personal zugewiesen", Notification.Type.WARNING_MESSAGE);
				}
			}
			
		}catch(Exception e){
			System.err.println(e.getMessage());
			
		}
		
	}
}
