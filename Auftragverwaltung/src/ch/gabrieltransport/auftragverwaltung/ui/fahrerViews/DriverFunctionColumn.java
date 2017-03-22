
package ch.gabrieltransport.auftragverwaltung.ui.fahrerViews;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.xdev.ui.entitycomponent.table.XdevTable;

import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerfunktion;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerfunktionmap;

public class DriverFunctionColumn implements ColumnGenerator {

	@Override
	public Object generateCell(Table table, Object itemId, Object columnId) {
		Fahrer bean = getBean(table, itemId);
		List<String> function = new ArrayList<String>();
		List<String> fahrerFunction = new ArrayList<String>();
		for(Fahrerfunktionmap fm: bean.getFahrerfunktionmaps()){
			String description = fm.getFahrerfunktion().getBeschreibung();
			if(description.startsWith("Fahrer")){
				fahrerFunction.add(description);
			}else{
				function.add(description);
			}
		}
		function = function.stream().map(s-> s.substring(0, 1).toUpperCase()).collect(Collectors.toList());;
		Optional<String> fahrerF =  fahrerFunction.stream().sorted(Comparator.reverseOrder())
														   .map(s -> s.replaceFirst("^Fahrer", ""))
							   							   .findFirst();
		if(fahrerF.isPresent()){
			function.add(fahrerF.get());
		}
		Optional<String> fahrerFConcat = function.stream().sorted().reduce((s,t) -> s + ", " + t);
		return (fahrerFConcat.isPresent()) ? fahrerFConcat.get() : "";
	}

	@SuppressWarnings("unchecked")
	public Fahrer getBean(Table table, Object itemId) {
		return ((XdevTable<Fahrer>) table).getBeanItem(itemId).getBean();
	}
}
