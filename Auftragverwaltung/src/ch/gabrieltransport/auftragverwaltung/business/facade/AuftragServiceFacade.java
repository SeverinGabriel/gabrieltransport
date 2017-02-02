package ch.gabrieltransport.auftragverwaltung.business.facade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.transaction.*;

import com.google.common.collect.Sets;

import ch.gabrieltransport.auftragverwaltung.business.BOAuftragService;
import ch.gabrieltransport.auftragverwaltung.business.BOFahrerAuftragService;
import ch.gabrieltransport.auftragverwaltung.business.BOFahrzeugAuftragService;
import ch.gabrieltransport.auftragverwaltung.dal.AuftragDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerauftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;

public class AuftragServiceFacade {
	
	
	private BOAuftragService boAuftragService = new BOAuftragService();
	private BOFahrzeugAuftragService boFzAService = new BOFahrzeugAuftragService();
	private BOFahrerAuftragService boFaAService = new BOFahrerAuftragService();
	
	@Transactional
	public void deleteAuftrag(Auftrag auftrag){
		try{
	    	
	    	boAuftragService.deleteAuftrag(auftrag);
	    	
		}catch(Exception e){
			System.err.println(e.getMessage());
			
		}
	}
	
	@Transactional
	public void persistAuftrag(Auftrag auftrag, Fahrzeug fahrzeug, List<Fahrerauftrag> fahrer){
		try{
	    	List<Fahrzeugauftrag> fahrzeugauftraege = Arrays.asList(createTaskFromVehicle(auftrag, fahrzeug));
	    	linkTasks(auftrag, fahrzeugauftraege, fahrer);
	    	boAuftragService.persistAuftrag(auftrag);
	    	
		}catch(Exception e){
			System.err.println(e.getMessage());
			
		}
	}
	
	@Transactional
	public Auftrag mergeAuftrag(Auftrag auftrag, Fahrzeug fahrzeug, List<Fahrerauftrag> fahrer){
		try{	
			deleteRelationsFromTask(auftrag);
			List<Fahrzeugauftrag> fahrzeugauftraege = Arrays.asList(createTaskFromVehicle(auftrag, fahrzeug));
	    	linkTasks(auftrag, fahrzeugauftraege, fahrer);
	    	return boAuftragService.mergeAuftrag(auftrag);
	    	
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return null;
		
	}
	
	private void linkTasks(Auftrag auftrag, List<Fahrzeugauftrag> fahrzeug, List<Fahrerauftrag> fahrer){
		auftrag.setFahrerauftrags(Sets.newHashSet(fahrer));
		auftrag.setFahrzeugauftrags(Sets.newHashSet(fahrzeug));
	}
	
	public void deleteRelationsFromTask(Auftrag auftrag){
		boFaAService.deleteAllFromTask(auftrag);
		boFzAService.deleteAllFromAuftrag(auftrag);
	}
	
	private Fahrzeugauftrag createTaskFromVehicle(Auftrag auftrag, Fahrzeug fahrzeug){
		Fahrzeugauftrag fzA = new Fahrzeugauftrag();
    	fzA.setAuftrag(auftrag);
    	fzA.setVonDatum(auftrag.getVonDatum());
    	fzA.setBisDatum(auftrag.getBisDatum());
    	fzA.setFahrzeug(fahrzeug);
    	return fzA;
    	
	} 
}
