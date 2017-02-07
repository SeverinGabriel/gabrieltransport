
package ch.gabrieltransport.auftragverwaltung.ui;

import java.time.LocalDateTime;
import ch.gabrieltransport.auftragverwaltung.business.refresher.Broadcaster;
import ch.gabrieltransport.auftragverwaltung.business.refresher.Broadcaster.BroadcastListener;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.ClientConnector;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.xdev.dal.DAOs;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevHorizontalSplitPanel;
import com.xdev.ui.XdevLabel;
import com.xdev.ui.XdevTextField;
import com.xdev.ui.XdevView;
import com.xdev.ui.entitycomponent.XdevBeanContainer;
import com.xdev.ui.entitycomponent.combobox.XdevComboBox;
import com.xdev.ui.entitycomponent.table.XdevTable;
import com.xdev.ui.filter.XdevContainerFilterComponent;
import com.xdev.ui.masterdetail.MasterDetail;
import com.xdev.ui.util.NestedProperty;
import com.xdev.ui.util.wizard.XDEV;

import ch.gabrieltransport.auftragverwaltung.business.refresher.GuiListenerSingleton;
import ch.gabrieltransport.auftragverwaltung.dal.FahrerDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer_;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerfunktion;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.entities.FahrzeugFunktion_;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug_;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;
import ch.gabrieltransport.auftragverwaltung.ui.calendar.CurrentWeek;
import ch.gabrieltransport.auftragverwaltung.ui.calendar.Week;

public class MainView extends XdevView implements BroadcastListener {

	
	private CurrentWeek currWeek = new CurrentWeek();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
	private List<WeekDayEmployeeColumn> weekDayEmployee = new ArrayList<WeekDayEmployeeColumn>();
	/**
	 * 
	 */
	public MainView() {
		super();
		this.initUI();
		//Broadcaster.register(this);
		//GuiListenerSingleton.getInstance().addObserver(this);
		tblEmployee.addValueChangeListener(lblWeek);
		updateLabels();
		Broadcaster.register(this);
		
}
	

	public void update(){
		tblTask.refreshRowCache();
		tblEmployee.refreshRowCache();
	}
		
	public void updateLabels(){
		Week currentWeek = currWeek.getCurrentWeek();
		lblWeek.setValue("Woche " + currentWeek.getWeekNr());
		LocalDateTime start = currentWeek.getStartDate();
		LocalDateTime end = start.plusDays(6);
		lblWeekInterval.setValue(start.format(formatter) + " - " + end.format(formatter)); // "29.08 - 04.09"
		lblYear.setValue(String.valueOf(start.getYear()));
	}

	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #button2}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button2_buttonClick(Button.ClickEvent event) {
		currWeek.setNextWeek();
		updateLabels();
		update();
	}

	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #button}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button_buttonClick(Button.ClickEvent event) {
		currWeek.setPreviousWeek();
		updateLabels();
		update();
	}
	
	
	/**
	 * Event handler delegate method for the {@link XdevTable}
	 * {@link #tblEmployee}.
	 *
	 * @see Table.HeaderClickListener#headerClick(Table.HeaderClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void tblEmployee_headerClick(Table.HeaderClickEvent event) {
		tblEmployee.setVisible(false);
		btnExpandPersonal.setVisible(true);
	}


	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #btnExpandPersonal}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void btnExpandPersonal_buttonClick(Button.ClickEvent event) {
		tblEmployee.setVisible(true);
		btnExpandPersonal.setVisible(false);
	}


	/**
	 * Event handler delegate method for the {@link XdevTable} {@link #tblEmployee}.
	 *
	 * @see Table.ColumnResizeListener#columnResize(Table.ColumnResizeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void tblEmployee_columnResize(Table.ColumnResizeEvent event) {
		String test = event.getPropertyId().toString();
		tblTask.setColumnWidth(test, event.getCurrentWidth());
	}


/**
	 * Event handler delegate method for the {@link XdevTable} {@link #tblTask}.
	 *
	 * @see Table.ColumnResizeListener#columnResize(Table.ColumnResizeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void tblTask_columnResize(Table.ColumnResizeEvent event) {
		String test = event.getPropertyId().toString();
		tblEmployee.setColumnWidth(test, event.getCurrentWidth());
	}
				

		/**
	 * Event handler delegate method for the {@link XdevComboBox} {@link #comboBox}.
	 *
	 * @see Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void comboBox_valueChange(Property.ValueChangeEvent event) {
		XdevBeanContainer<Fahrer> myDriverList = tblEmployee.getBeanContainerDataSource();
		myDriverList.addAll(new FahrerDAO().findAllByFunktion(comboBox.getSelectedItem().getBean()));
		tblEmployee.setContainerDataSource(myDriverList);
	}

private int counter = 0;
/**
		 * Event handler delegate method for the {@link XdevButton} {@link #button3}.
		 *
		 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
		 * @eventHandlerDelegate Do NOT delete, used by UI designer!
		 */
		private void button3_buttonClick(Button.ClickEvent event) {
			counter ++;
			Broadcaster.broadcast("Clicked "+ String.valueOf(counter));
		}
		
		public void updateLabel(String message){
			label.setValue(message);
		}


/*
 * WARNING: Do NOT edit!<br>The content of this method is always regenerated by
 * the UI designer.
 */
// <generated-code name="initUI">
private void initUI() {
	this.gridLayout = new XdevGridLayout();
	this.horizontalLayout = new XdevHorizontalLayout();
	this.button = new XdevButton();
	this.lblWeek = new XdevLabel();
	this.button2 = new XdevButton();
	this.horizontalLayout2 = new XdevHorizontalLayout();
	this.lblWeekInterval = new XdevLabel();
	this.horizontalSplitPanel = new XdevHorizontalSplitPanel();
	this.lblYear = new XdevLabel();
	this.btnExpandPersonal = new XdevButton();
	this.horizontalLayout3 = new XdevHorizontalLayout();
	this.button3 = new XdevButton();
	this.label = new XdevLabel();
	this.comboBox = new XdevComboBox<>();
	this.containerFilterComponent2 = new XdevContainerFilterComponent();
	this.tblEmployee = new XdevTable<>();
	this.containerFilterComponent = new XdevContainerFilterComponent();
	this.tblTask = new XdevTable<>();

	this.horizontalLayout.setMargin(new MarginInfo(false));
	this.button.setIcon(FontAwesome.CARET_LEFT);
	this.button.setCaption("");
	this.button2.setIcon(FontAwesome.CARET_RIGHT);
	this.button2.setCaption("");
	this.horizontalLayout2.setMargin(new MarginInfo(false));
	this.lblWeekInterval.setValue("01.08 - 07.08");
	this.horizontalSplitPanel.setSplitPosition(2.0F, Unit.PERCENTAGE);
	this.lblYear.setValue("2016");
	this.btnExpandPersonal.setIcon(FontAwesome.CARET_DOWN);
	this.btnExpandPersonal.setCaption("Personal");
	this.btnExpandPersonal.setVisible(false);
	this.horizontalLayout3.setMargin(new MarginInfo(false));
	this.button3.setCaption("Button");
	this.label.setValue("Label");
	this.comboBox.setContainerDataSource(Fahrerfunktion.class);
	this.tblEmployee.setCaption("Personal");
	this.tblEmployee.setResponsive(true);
	this.tblEmployee.setContainerDataSource(Fahrer.class, DAOs.get(FahrerDAO.class).findAll());
	this.tblEmployee.addGeneratedColumn("Montag", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.addGeneratedColumn("Dienstag", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.addGeneratedColumn("Mittwoch", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.addGeneratedColumn("Donnerstag", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.addGeneratedColumn("Freitag", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.addGeneratedColumn("Samstag", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.addGeneratedColumn("Sonntag", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.setVisibleColumns(Fahrer_.vorname.getName(), Fahrer_.nachname.getName(), "Montag", "Dienstag",
			"Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag");
	this.tblEmployee.setColumnWidth("vorname", 100);
	this.tblEmployee.setColumnWidth("nachname", 100);
	this.tblEmployee.setColumnExpandRatio("Montag", 0.1F);
	this.tblEmployee.setColumnExpandRatio("Dienstag", 0.1F);
	this.tblEmployee.setColumnExpandRatio("Mittwoch", 0.1F);
	this.tblEmployee.setColumnExpandRatio("Donnerstag", 0.1F);
	this.tblEmployee.setColumnExpandRatio("Freitag", 0.1F);
	this.tblEmployee.setColumnExpandRatio("Samstag", 0.1F);
	this.tblEmployee.setColumnExpandRatio("Sonntag", 0.1F);
	this.tblTask.setCaption("Aufträge");
	this.tblTask.setColumnCollapsingAllowed(true);
	this.tblTask.setResponsive(true);
	this.tblTask.setContainerDataSource(Fahrzeug.class,
			NestedProperty.of(Fahrzeug_.fahrzeugFunktion, FahrzeugFunktion_.beschreibung));
	this.tblTask.addGeneratedColumn("Montag", new WeekDayTaskColumn.Generator());
	this.tblTask.addGeneratedColumn("Dienstag", new WeekDayTaskColumn.Generator());
	this.tblTask.addGeneratedColumn("Mittwoch", new WeekDayTaskColumn.Generator());
	this.tblTask.addGeneratedColumn("Donnerstag", new WeekDayTaskColumn.Generator());
	this.tblTask.addGeneratedColumn("Freitag", new WeekDayTaskColumn.Generator());
	this.tblTask.addGeneratedColumn("Samstag", new WeekDayTaskColumn.Generator());
	this.tblTask.addGeneratedColumn("Sonntag", new WeekDayTaskColumn.Generator());
	this.tblTask.setVisibleColumns(Fahrzeug_.idfahrzeug.getName(), Fahrzeug_.kennzeichen.getName(),
			Fahrzeug_.gewicht.getName(),
			NestedProperty.path(Fahrzeug_.fahrzeugFunktion, FahrzeugFunktion_.beschreibung), "Montag", "Dienstag",
			"Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag");
	this.tblTask.setColumnHeader("idfahrzeug", "#");
	this.tblTask.setColumnWidth("idfahrzeug", 25);
	this.tblTask.setColumnHeader("kennzeichen", "Kennzeichen");
	this.tblTask.setColumnWidth("kennzeichen", 100);
	this.tblTask.setColumnWidth("gewicht", 75);
	this.tblTask.setColumnHeader("Montag", "Montag");
	this.tblTask.setColumnExpandRatio("Montag", 0.1F);
	this.tblTask.setColumnHeader("Dienstag", "Dienstag");
	this.tblTask.setColumnExpandRatio("Dienstag", 0.1F);
	this.tblTask.setColumnHeader("Mittwoch", "Mittwoch");
	this.tblTask.setColumnExpandRatio("Mittwoch", 0.1F);
	this.tblTask.setColumnHeader("Donnerstag", "Donnerstag");
	this.tblTask.setColumnExpandRatio("Donnerstag", 0.1F);
	this.tblTask.setColumnHeader("Freitag", "Freitag");
	this.tblTask.setColumnExpandRatio("Freitag", 0.1F);
	this.tblTask.setColumnHeader("Samstag", "Samstag");
	this.tblTask.setColumnExpandRatio("Samstag", 0.1F);
	this.tblTask.setColumnHeader("Sonntag", "Sonntag");
	this.tblTask.setColumnExpandRatio("Sonntag", 0.1F);

	this.containerFilterComponent.setContainer(this.tblTask.getBeanContainerDataSource(), "idfahrzeug", "kennzeichen",
			"gewicht", "fahrzeugFunktion");
	this.containerFilterComponent.setSearchableProperties("kennzeichen", "gewicht", "fahrzeugFunktion.beschreibung");

	this.button.setSizeUndefined();
	this.horizontalLayout.addComponent(this.button);
	this.horizontalLayout.setComponentAlignment(this.button, Alignment.MIDDLE_CENTER);
	this.lblWeek.setSizeUndefined();
	this.horizontalLayout.addComponent(this.lblWeek);
	this.horizontalLayout.setComponentAlignment(this.lblWeek, Alignment.MIDDLE_CENTER);
	this.button2.setSizeUndefined();
	this.horizontalLayout.addComponent(this.button2);
	this.horizontalLayout.setComponentAlignment(this.button2, Alignment.MIDDLE_CENTER);
	final CustomComponent horizontalLayout_spacer = new CustomComponent();
	horizontalLayout_spacer.setSizeFull();
	this.horizontalLayout.addComponent(horizontalLayout_spacer);
	this.horizontalLayout.setExpandRatio(horizontalLayout_spacer, 1.0F);
	this.lblWeekInterval.setSizeUndefined();
	this.horizontalLayout2.addComponent(this.lblWeekInterval);
	this.horizontalLayout2.setComponentAlignment(this.lblWeekInterval, Alignment.MIDDLE_CENTER);
	this.horizontalSplitPanel.setWidth(15, Unit.PERCENTAGE);
	this.horizontalSplitPanel.setHeight(100, Unit.PERCENTAGE);
	this.horizontalLayout2.addComponent(this.horizontalSplitPanel);
	this.horizontalLayout2.setComponentAlignment(this.horizontalSplitPanel, Alignment.MIDDLE_CENTER);
	this.horizontalLayout2.setExpandRatio(this.horizontalSplitPanel, 100.0F);
	this.lblYear.setSizeUndefined();
	this.horizontalLayout2.addComponent(this.lblYear);
	this.horizontalLayout2.setComponentAlignment(this.lblYear, Alignment.MIDDLE_CENTER);
	this.button3.setSizeUndefined();
	this.horizontalLayout3.addComponent(this.button3);
	this.horizontalLayout3.setComponentAlignment(this.button3, Alignment.MIDDLE_CENTER);
	this.label.setSizeUndefined();
	this.horizontalLayout3.addComponent(this.label);
	this.horizontalLayout3.setComponentAlignment(this.label, Alignment.MIDDLE_CENTER);
	this.comboBox.setWidth(100, Unit.PERCENTAGE);
	this.comboBox.setHeight(-1, Unit.PIXELS);
	this.horizontalLayout3.addComponent(this.comboBox);
	this.horizontalLayout3.setComponentAlignment(this.comboBox, Alignment.MIDDLE_CENTER);
	this.horizontalLayout3.setExpandRatio(this.comboBox, 30.0F);
	this.containerFilterComponent2.setWidth(100, Unit.PERCENTAGE);
	this.containerFilterComponent2.setHeight(-1, Unit.PIXELS);
	this.horizontalLayout3.addComponent(this.containerFilterComponent2);
	this.horizontalLayout3.setComponentAlignment(this.containerFilterComponent2, Alignment.MIDDLE_RIGHT);
	this.horizontalLayout3.setExpandRatio(this.containerFilterComponent2, 90.0F);
	this.gridLayout.setColumns(1);
	this.gridLayout.setRows(7);
	this.horizontalLayout.setWidth(195, Unit.PIXELS);
	this.horizontalLayout.setHeight(40, Unit.PIXELS);
	this.gridLayout.addComponent(this.horizontalLayout, 0, 0);
	this.gridLayout.setComponentAlignment(this.horizontalLayout, Alignment.MIDDLE_CENTER);
	this.horizontalLayout2.setWidth(195, Unit.PIXELS);
	this.horizontalLayout2.setHeight(29, Unit.PIXELS);
	this.gridLayout.addComponent(this.horizontalLayout2, 0, 1);
	this.gridLayout.setComponentAlignment(this.horizontalLayout2, Alignment.MIDDLE_CENTER);
	this.btnExpandPersonal.setSizeUndefined();
	this.gridLayout.addComponent(this.btnExpandPersonal, 0, 2);
	this.horizontalLayout3.setWidth(100, Unit.PERCENTAGE);
	this.horizontalLayout3.setHeight(41, Unit.PIXELS);
	this.gridLayout.addComponent(this.horizontalLayout3, 0, 3);
	this.tblEmployee.setSizeFull();
	this.gridLayout.addComponent(this.tblEmployee, 0, 4);
	this.containerFilterComponent.setWidth(100, Unit.PERCENTAGE);
	this.containerFilterComponent.setHeight(-1, Unit.PIXELS);
	this.gridLayout.addComponent(this.containerFilterComponent, 0, 5);
	this.tblTask.setSizeFull();
	this.gridLayout.addComponent(this.tblTask, 0, 6);
	this.gridLayout.setColumnExpandRatio(0, 100.0F);
	this.gridLayout.setRowExpandRatio(4, 30.0F);
	this.gridLayout.setRowExpandRatio(6, 100.0F);
	this.gridLayout.setSizeFull();
	this.setContent(this.gridLayout);
	this.setSizeFull();

	this.button.addClickListener(event -> this.button_buttonClick(event));
	this.button2.addClickListener(event -> this.button2_buttonClick(event));
	this.btnExpandPersonal.addClickListener(event -> this.btnExpandPersonal_buttonClick(event));
	this.button3.addClickListener(event -> this.button3_buttonClick(event));
	this.comboBox.addValueChangeListener(new Property.ValueChangeListener() {
		@Override
		public void valueChange(Property.ValueChangeEvent event) {
			MainView.this.comboBox_valueChange(event);
		}
	});
	this.tblEmployee.addHeaderClickListener(event -> this.tblEmployee_headerClick(event));
	this.tblEmployee.addColumnResizeListener(event -> this.tblEmployee_columnResize(event));
	this.tblTask.addColumnResizeListener(event -> this.tblTask_columnResize(event));
} // </generated-code>

// <generated-code name="variables">
private XdevButton button, button2, btnExpandPersonal, button3;
private XdevLabel lblWeek, lblWeekInterval, lblYear, label;
private XdevHorizontalLayout horizontalLayout, horizontalLayout2, horizontalLayout3;
private XdevTable<Fahrzeug> tblTask;
private XdevComboBox<Fahrerfunktion> comboBox;
private XdevGridLayout gridLayout;
private XdevHorizontalSplitPanel horizontalSplitPanel;
private XdevContainerFilterComponent containerFilterComponent2, containerFilterComponent;
private XdevTable<Fahrer> tblEmployee;
// </generated-code>

// </generated-code>

public void receiveBroadcast(String message) {
		tblTask.refreshRowCache();
		tblEmployee.refreshRowCache();
	
}




}
