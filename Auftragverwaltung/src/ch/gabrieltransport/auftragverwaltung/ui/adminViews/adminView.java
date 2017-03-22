package ch.gabrieltransport.auftragverwaltung.ui.adminViews;

import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger_;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer_;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug_;
import ch.gabrieltransport.auftragverwaltung.ui.fahrzeugViews.DeleteFahrzeugColumn;
import ch.gabrieltransport.auftragverwaltung.ui.fahrzeugViews.EditFahrzeugColumn;

import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevTabSheet;
import com.xdev.ui.XdevView;
import com.xdev.ui.entitycomponent.table.XdevTable;

public class adminView extends XdevView {

	/**
	 * 
	 */
	public adminView() {
		super();
		this.initUI();
	}

	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated by
	 * the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.gridLayout = new XdevGridLayout();
		this.tabSheet = new XdevTabSheet();
		this.table = new XdevTable<>();
		this.table2 = new XdevTable<>();
		this.table3 = new XdevTable<>();
	
		this.tabSheet.setStyleName("framed");
		this.table.setEditable(true);
		this.table.setContainerDataSource(Fahrer.class);
		this.table.setVisibleColumns(Fahrer_.nachname.getName(), Fahrer_.vorname.getName(), Fahrer_.telefon.getName());
		this.table2.setContainerDataSource(Fahrzeug.class);
		this.table2.addGeneratedColumn("generated2", new EditFahrzeugColumn.Generator());
		this.table2.addGeneratedColumn("generated", new DeleteFahrzeugColumn.Generator());
		this.table2.setVisibleColumns(Fahrzeug_.kennzeichen.getName(), Fahrzeug_.nutzlast.getName(),
				Fahrzeug_.nummer.getName(), Fahrzeug_.anhaenger.getName(), "generated2", "generated");
		this.table3.setEditable(true);
		this.table3.setContainerDataSource(Anhaenger.class);
		this.table3.setVisibleColumns(Anhaenger_.kennzeichen.getName(), Anhaenger_.anhaengertyp.getName(),
				Anhaenger_.fahrzeugFunktion.getName(), Anhaenger_.nummer.getName(), Anhaenger_.nutzlast.getName());
	
		this.table.setSizeFull();
		this.tabSheet.addTab(this.table, "Tab", null);
		this.table2.setSizeFull();
		this.tabSheet.addTab(this.table2, "Tab", null);
		this.table3.setSizeFull();
		this.tabSheet.addTab(this.table3, "Tab", null);
		this.tabSheet.setSelectedTab(this.table2);
		this.gridLayout.setColumns(1);
		this.gridLayout.setRows(1);
		this.tabSheet.setSizeFull();
		this.gridLayout.addComponent(this.tabSheet, 0, 0);
		this.gridLayout.setColumnExpandRatio(0, 100.0F);
		this.gridLayout.setRowExpandRatio(0, 100.0F);
		this.gridLayout.setSizeFull();
		this.setContent(this.gridLayout);
		this.setSizeFull();
	} // </generated-code>

	// <generated-code name="variables">
	private XdevTable<Anhaenger> table3;
	private XdevTable<Fahrzeug> table2;
	private XdevTabSheet tabSheet;
	private XdevGridLayout gridLayout;
	private XdevTable<Fahrer> table;
	// </generated-code>

}
