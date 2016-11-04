
package ch.gabrieltransport.auftragverwaltung.ui;

import java.time.LocalDateTime;
import java.util.List;

import com.vaadin.event.ContextClickEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.Window;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevVerticalLayout;
import com.xdev.ui.entitycomponent.table.XdevTable;

import ch.gabrieltransport.auftragverwaltung.business.facade.AuftragServiceFacade;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.ui.calendar.CurrentWeek;

public class WeekDayGuiGenerator extends XdevHorizontalLayout{

	public static class Generator implements ColumnGenerator {
		@Override
		public Object generateCell(Table table, Object itemId, Object columnId) {

			return new WeekDayGuiGenerator(table, itemId, columnId);
		}
	}

	private final Table customizedTable;
	private final Object itemId;
	private final Object columnId;
	

	private WeekDayGuiGenerator(Table customizedTable, Object itemId, Object columnId) {
		super();

		this.customizedTable = customizedTable;
		
		this.itemId = itemId;
		this.columnId = columnId;

		this.initUI();
		
		update();	
	}

	public Table getTable() {
		return customizedTable;
	}

	public Object getItemId() {
		return itemId;
	}

	public Object getColumnId() {
		return columnId;
	}

	@SuppressWarnings("unchecked")
	public Fahrzeug getBean() {
		return ((XdevTable<Fahrzeug>) getTable()).getContainerDataSource().getItem(getItemId()).getBean();
	}

	
	private void selectItem() {
		getTable().select(getItemId());
	}
	
	private LocalDateTime getDateForDay(){
		int dayOfWeekOffset = 0;
		switch(getColumnId().toString()){
		case "Montag":
			dayOfWeekOffset = 0;
			break;
		case "Dienstag":
			dayOfWeekOffset = 1;
			break;
		case "Mittwoch":
			dayOfWeekOffset = 2;
			break;
		case "Donnerstag":
			dayOfWeekOffset = 3;
			break;
		case "Freitag":
			dayOfWeekOffset = 4;
			break;
		case "Samstag":
			dayOfWeekOffset = 5;
			break;
		case "Sonntag":
			dayOfWeekOffset = 6;
			break;
		}
		return (new CurrentWeek()).getCurrentWeek().getStartDate().plusDays(dayOfWeekOffset);
	}

	/**
	 * Event handler delegate method for the {@link XdevHorizontalLayout}.
	 *
	 * @see ContextClickEvent.ContextClickListener#contextClick(ContextClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void this_contextClick(ContextClickEvent event) {
		Window win = new Window();
		taskDetail taskWindow = new taskDetail(getDateForDay(), getBean(),
				   new taskDetail.Callback() {
				      public void onDialogResult(boolean result) {
				    	  customizedTable.refreshRowCache();
				      }
				   });
		win.setWidth("600");
		win.setHeight("850");
		win.center();
		
		win.setModal(true);
		win.setContent(taskWindow);
		this.getUI().addWindow(win);
		
		
		
		
		
	}

	
	public void update() {
		verticalLayout2.removeAllComponents();
		final AuftragServiceFacade auftragServiceFacade = new AuftragServiceFacade();
		List<Auftrag> auftraege = auftragServiceFacade.findAuftrageon(getDateForDay(), getBean());
		for(Auftrag auftrag: auftraege){
			XdevButton auftragField = new XdevButton();
			auftragField.setCaption(auftrag.getBezeichung());
			auftragField.addStyleName("borderless");
			auftragField.setSizeFull();
			auftragField.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(Button.ClickEvent event) {
					Window win = new Window();
					win.setWidth("600");
					win.setHeight("850");
					win.center();
					
					win.setModal(true);
					win.setContent(new taskDetail(auftrag));
					getUI().addWindow(win);
					
				}
			});
					
			verticalLayout2.addComponent(auftragField);
		}
		
	}
	
	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated
	 * by the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.verticalLayout = new XdevVerticalLayout();
		this.verticalLayout2 = new XdevVerticalLayout();
	
		this.setSpacing(false);
		this.setMargin(new MarginInfo(false));
		this.verticalLayout.setSpacing(false);
		this.verticalLayout.setMargin(new MarginInfo(false));
	
		this.verticalLayout2.setSizeFull();
		this.verticalLayout.addComponent(this.verticalLayout2);
		this.verticalLayout.setComponentAlignment(this.verticalLayout2, Alignment.MIDDLE_CENTER);
		this.verticalLayout.setExpandRatio(this.verticalLayout2, 0.2F);
		this.verticalLayout.setSizeFull();
		this.addComponent(this.verticalLayout);
		this.setComponentAlignment(this.verticalLayout, Alignment.MIDDLE_CENTER);
		this.setExpandRatio(this.verticalLayout, 0.1F);
		this.setWidth(100, Unit.PERCENTAGE);
		this.setHeight(160, Unit.PIXELS);
	
		this.addContextClickListener(event -> this.this_contextClick(event));
	} // </generated-code>

	
		



	// <generated-code name="variables">
	private XdevVerticalLayout verticalLayout, verticalLayout2; // </generated-code>


}