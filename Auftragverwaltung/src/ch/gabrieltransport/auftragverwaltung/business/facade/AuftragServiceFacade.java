package ch.gabrieltransport.auftragverwaltung.business.facade;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import ch.gabrieltransport.auftragverwaltung.business.BOAuftragService;
import ch.gabrieltransport.auftragverwaltung.dal.AuftragDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;

public class AuftragServiceFacade {
	
	private final AuftragDAO auftragDAO = new AuftragDAO();
	private BOAuftragService boAuftragService = new BOAuftragService();
	
	@Transactional
	public void deleteAuftrag(Auftrag auftrag){
		try{
	    	
	    	boAuftragService.deleteAuftrag(auftrag);
	    	
		}catch(Exception e){
			System.err.println(e.getMessage());
			auftragDAO.rollback();
		}
	}
	
	@Transactional
	public void persistAuftrag(Auftrag auftrag, Fahrzeug fahrzeug, List<Fahrer> fahrer){
		try{
	    	Fahrzeugauftrag fzA = new Fahrzeugauftrag();
	    	fzA.setAuftrag(auftrag);
	    	fzA.setVonDatum(auftrag.getVonDatum());
	    	fzA.setBisDatum(auftrag.getBisDatum());
	    	fzA.setFahrzeug(fahrzeug);
	    	
	    	
	    	boAuftragService.persistAuftrag(auftrag, fzA);
	    	
		}catch(Exception e){
			System.err.println(e.getMessage());
			auftragDAO.rollback();
		}
	}
	
	@Transactional
	public List<Auftrag> findAuftrageon(LocalDateTime date, Fahrzeug fahrzeug){
		try{
	    	
	    	return boAuftragService.findAuftrageon(date, fahrzeug);
	    	
		}catch(Exception e){
			System.err.println(e.getMessage());
			auftragDAO.rollback();
		}
		return null;
		
	}
}
