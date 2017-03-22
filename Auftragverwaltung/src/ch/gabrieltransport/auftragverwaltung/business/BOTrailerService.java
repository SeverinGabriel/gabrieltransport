package ch.gabrieltransport.auftragverwaltung.business;

import java.util.List;

import ch.gabrieltransport.auftragverwaltung.dal.AnhaengerDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger;

public class BOTrailerService {

	private AnhaengerDAO trailerDAO = new AnhaengerDAO();
	public void deleteTrailer(Anhaenger trailer){
		trailerDAO.remove(trailer);
	}
}
