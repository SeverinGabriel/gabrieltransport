package ch.gabrieltransport.auftragverwaltung.business.facade;

import javax.transaction.Transactional;

import ch.gabrieltransport.auftragverwaltung.business.BOFahrerAuftragService;
import ch.gabrieltransport.auftragverwaltung.business.BOFahrerService;
import ch.gabrieltransport.auftragverwaltung.business.BOTrailerService;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;

public class TrailerServiceFacade {

	private BOTrailerService boTrailerService = new BOTrailerService();
	
	@Transactional
	public void deleteTrailer(Anhaenger trailer){
		boTrailerService.deleteTrailer(trailer);
	}
	
}
