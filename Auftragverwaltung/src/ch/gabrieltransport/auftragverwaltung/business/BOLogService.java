package ch.gabrieltransport.auftragverwaltung.business;


import java.time.LocalDateTime;
import java.util.Set;

import com.xdev.security.authentication.ui.Authentication;

import ch.gabrieltransport.auftragverwaltung.dal.LogDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Log;
import ch.gabrieltransport.auftragverwaltung.entities.Role;
import ch.gabrieltransport.auftragverwaltung.entities.User;

public class BOLogService {

	private final LogDAO logDAO = new LogDAO();
	
	public final void logMessage(String log) {
		String username = Authentication.getUser().name();
		Set<com.xdev.security.authorization.Role> roles = Authentication.getUser().effectiveRoles();
		if (username != null){
			Log logmessage = new Log(username, log, LocalDateTime.now());
			logDAO.persist(logmessage);
		}
		
		
    }

    
}
