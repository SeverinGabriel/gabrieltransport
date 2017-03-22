package ch.gabrieltransport.auftragverwaltung.business;

import ch.gabrieltransport.auftragverwaltung.dal.FahrerDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;

public class BOFahrerService {

	private FahrerDAO employeeDAO = new FahrerDAO();
	public void deleteEmployee(Fahrer employee){
		employeeDAO.remove(employee);
	}
}
