
package ch.gabrieltransport.auftragverwaltung.ui;

import java.time.LocalDateTime;
import ch.gabrieltransport.auftragverwaltung.business.refresher.Broadcaster;
import ch.gabrieltransport.auftragverwaltung.business.refresher.Broadcaster.BroadcastListener;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.text.StyledEditorKit.BoldAction;

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
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.xdev.dal.DAOs;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevHorizontalSplitPanel;
import com.xdev.ui.XdevLabel;
import com.xdev.ui.XdevPanel;
import com.xdev.ui.XdevTabSheet;
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
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger_;
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
	private Boolean hasChanged = false;
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
		updateLabels();
		tblEmployee.getBeanContainerDataSource().addAll(new FahrerDAO().findAll());
		Broadcaster.register(this);
		
}
	

	public void update(){
		hasChanged = true;
		tblTask.refreshRowCache();
		
        /**Layout tab = (Layout) tabSheet.getSelectedTab();

        // Get the tab caption from the tab object
        String caption = tabSheet.getTab(tab).getCaption();
		if(caption == "Personal"){
			tblEmployee.refreshRowCache();
		}
		else if(caption == "Anhänger"){
			tblTrailer.refreshRowCache();
		}**/
	}
		
	public void updateLabels(){
		Week currentWeek = currWeek.getCurrentWeek();
		
		btnWeek.setCaption("Woche " + currentWeek.getWeekNr());
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
		tblEmployee.getBeanContainerDataSource().removeAllItems();
		if (comboBox.getSelectedItem() != null){
			tblEmployee.getBeanContainerDataSource().addAll(new FahrerDAO().findAllByFunktion(comboBox.getSelectedItem().getBean()));
		} else{
			tblEmployee.getBeanContainerDataSource().addAll(new FahrerDAO().findAll());
		}
		
	}



/**
		 * Event handler delegate method for the {@link XdevButton} {@link #btnWeek}.
		 *
		 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
		 * @eventHandlerDelegate Do NOT delete, used by UI designer!
		 */
		private void btnWeek_buttonClick(Button.ClickEvent event) {
			currWeek.setWeek(new Week());
			updateLabels();
			update();
		}


/**
 * Event handler delegate method for the {@link XdevButton}
 * {@link #btnEmployee}.
 *
 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
 * @eventHandlerDelegate Do NOT delete, used by UI designer!
 */
private void btnEmployee_buttonClick(Button.ClickEvent event) {
	btnTrailer.setEnabled(true);
	btnEmployee.setEnabled(false);
	if (hasChanged) {
		hasChanged = false;
		tblEmployee.refreshRowCache();
	}
	pnTrailer.setVisible(false);
	pnEmployee.setVisible(true);
}


/**
 * Event handler delegate method for the {@link XdevButton} {@link #btnTrailer}.
 *
 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
 * @eventHandlerDelegate Do NOT delete, used by UI designer!
 */
private void btnTrailer_buttonClick(Button.ClickEvent event) {
	btnTrailer.setEnabled(false);
	btnEmployee.setEnabled(true);
	if (hasChanged) {
		hasChanged = false;
		tblTrailer.refreshRowCache();
	}
	pnEmployee.setVisible(false);
	pnTrailer.setVisible(true);
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
	this.btnWeek = new XdevButton();
	this.button2 = new XdevButton();
	this.horizontalLayout2 = new XdevHorizontalLayout();
	this.lblWeekInterval = new XdevLabel();
	this.horizontalSplitPanel = new XdevHorizontalSplitPanel();
	this.lblYear = new XdevLabel();
	this.horizontalLayout6 = new XdevHorizontalLayout();
	this.btnEmployee = new XdevButton();
	this.btnTrailer = new XdevButton();
	this.horizontalLayout5 = new XdevHorizontalLayout();
	this.pnEmployee = new XdevPanel();
	this.gridLayout4 = new XdevGridLayout();
	this.horizontalLayout4 = new XdevHorizontalLayout();
	this.comboBox = new XdevComboBox<>();
	this.containerFilterComponent2 = new XdevContainerFilterComponent();
	this.tblEmployee = new XdevTable<>();
	this.pnTrailer = new XdevPanel();
	this.gridLayout5 = new XdevGridLayout();
	this.horizontalLayout3 = new XdevHorizontalLayout();
	this.containerFilterComponent3 = new XdevContainerFilterComponent();
	this.tblTrailer = new XdevTable<>();
	this.containerFilterComponent = new XdevContainerFilterComponent();
	this.tblTask = new XdevTable<>();

	this.horizontalLayout.setMargin(new MarginInfo(false));
	this.button.setIcon(FontAwesome.CARET_LEFT);
	this.button.setCaption("");
	this.btnWeek.setCaption("");
	this.btnWeek.setStyleName("borderless");
	this.button2.setIcon(FontAwesome.CARET_RIGHT);
	this.button2.setCaption("");
	this.horizontalLayout2.setMargin(new MarginInfo(false));
	this.lblWeekInterval.setValue("01.08 - 07.08");
	this.horizontalSplitPanel.setSplitPosition(2.0F, Unit.PERCENTAGE);
	this.lblYear.setValue("2016");
	this.horizontalLayout6.setSpacing(false);
	this.horizontalLayout6.setMargin(new MarginInfo(false));
	this.btnEmployee.setCaption("Personal");
	this.btnEmployee.setStyleName("borderless");
	this.btnEmployee.setEnabled(false);
	this.btnTrailer.setCaption("Anhänger");
	this.btnTrailer.setStyleName("borderless");
	this.horizontalLayout5.setMargin(new MarginInfo(false));
	this.pnEmployee.setTabIndex(0);
	this.pnEmployee.setId("pnEmployee");
	this.gridLayout4.setMargin(new MarginInfo(false));
	this.horizontalLayout4.setMargin(new MarginInfo(false));
	this.comboBox.setContainerDataSource(Fahrerfunktion.class);
	this.tblEmployee.setResponsive(true);
	this.tblEmployee.setContainerDataSource(Fahrer.class, false);
	this.tblEmployee.addGeneratedColumn("Funktion", new DriverFunctionColumn());
	this.tblEmployee.addGeneratedColumn("Montag", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.addGeneratedColumn("Dienstag", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.addGeneratedColumn("Mittwoch", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.addGeneratedColumn("Donnerstag", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.addGeneratedColumn("Freitag", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.addGeneratedColumn("Samstag", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.addGeneratedColumn("Sonntag", new WeekDayEmployeeColumn.Generator());
	this.tblEmployee.setVisibleColumns(Fahrer_.vorname.getName(), Fahrer_.nachname.getName(), "Funktion", "Montag",
			"Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag");
	this.tblEmployee.setColumnHeader("vorname", "Vorname");
	this.tblEmployee.setColumnWidth("vorname", 110);
	this.tblEmployee.setColumnHeader("nachname", "Nachname");
	this.tblEmployee.setColumnWidth("nachname", 110);
	this.tblEmployee.setColumnWidth("Funktion", 95);
	this.tblEmployee.setColumnExpandRatio("Montag", 0.1F);
	this.tblEmployee.setColumnExpandRatio("Dienstag", 0.1F);
	this.tblEmployee.setColumnExpandRatio("Mittwoch", 0.1F);
	this.tblEmployee.setColumnExpandRatio("Donnerstag", 0.1F);
	this.tblEmployee.setColumnExpandRatio("Freitag", 0.1F);
	this.tblEmployee.setColumnExpandRatio("Samstag", 0.05F);
	this.tblEmployee.setColumnExpandRatio("Sonntag", 0.05F);
	this.pnTrailer.setVisible(false);
	this.pnTrailer.setId("pnTrailer");
	this.gridLayout5.setMargin(new MarginInfo(false));
	this.horizontalLayout3.setMargin(new MarginInfo(false));
	this.tblTrailer.setContainerDataSource(Anhaenger.class,
			NestedProperty.of(Anhaenger_.fahrzeugFunktion, FahrzeugFunktion_.beschreibung));
	this.tblTrailer.addGeneratedColumn("Montag", new WeekDayTrailerColumn.Generator());
	this.tblTrailer.addGeneratedColumn("Dienstag", new WeekDayTrailerColumn.Generator());
	this.tblTrailer.addGeneratedColumn("Mittwoch", new WeekDayTrailerColumn.Generator());
	this.tblTrailer.addGeneratedColumn("Donnerstag", new WeekDayTrailerColumn.Generator());
	this.tblTrailer.addGeneratedColumn("Freitag", new WeekDayTrailerColumn.Generator());
	this.tblTrailer.addGeneratedColumn("Samstag", new WeekDayTrailerColumn.Generator());
	this.tblTrailer.addGeneratedColumn("Sonntag", new WeekDayTrailerColumn.Generator());
	this.tblTrailer.setVisibleColumns(Anhaenger_.nummer.getName(), Anhaenger_.kennzeichen.getName(),
			Anhaenger_.nutzlast.getName(),
			NestedProperty.path(Anhaenger_.fahrzeugFunktion, FahrzeugFunktion_.beschreibung), "Montag", "Dienstag",
			"Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag");
	this.tblTrailer.setColumnHeader("nummer", "#");
	this.tblTrailer.setColumnWidth("nummer", 50);
	this.tblTrailer.setColumnHeader("kennzeichen", "Kennzeichen");
	this.tblTrailer.setColumnWidth("kennzeichen", 100);
	this.tblTrailer.setColumnHeader("nutzlast", "Nutzlast");
	this.tblTrailer.setColumnWidth("nutzlast", 75);
	this.tblTrailer.setColumnHeader("fahrzeugFunktion.beschreibung", "Funktion");
	this.tblTrailer.setColumnWidth("fahrzeugFunktion.beschreibung", 90);
	this.tblTrailer.setColumnHeader("Montag", "Montag");
	this.tblTrailer.setColumnExpandRatio("Montag", 0.1F);
	this.tblTrailer.setColumnHeader("Dienstag", "Dienstag");
	this.tblTrailer.setColumnExpandRatio("Dienstag", 0.1F);
	this.tblTrailer.setColumnHeader("Mittwoch", "Mittwoch");
	this.tblTrailer.setColumnExpandRatio("Mittwoch", 0.1F);
	this.tblTrailer.setColumnExpandRatio("Donnerstag", 0.1F);
	this.tblTrailer.setColumnExpandRatio("Freitag", 0.1F);
	this.tblTrailer.setColumnExpandRatio("Samstag", 0.05F);
	this.tblTrailer.setColumnExpandRatio("Sonntag", 0.05F);
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
	this.tblTask.setVisibleColumns(Fahrzeug_.nummer.getName(), Fahrzeug_.kennzeichen.getName(),
			Fahrzeug_.nutzlast.getName(),
			NestedProperty.path(Fahrzeug_.fahrzeugFunktion, FahrzeugFunktion_.beschreibung), "Montag", "Dienstag",
			"Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag");
	this.tblTask.setColumnHeader("nummer", "#");
	this.tblTask.setColumnWidth("nummer", 50);
	this.tblTask.setColumnHeader("kennzeichen", "Kennzeichen");
	this.tblTask.setColumnWidth("kennzeichen", 100);
	this.tblTask.setColumnHeader("nutzlast", "Nutzlast");
	this.tblTask.setColumnWidth("nutzlast", 75);
	this.tblTask.setColumnHeader("fahrzeugFunktion.beschreibung", "Funktion");
	this.tblTask.setColumnWidth("fahrzeugFunktion.beschreibung", 90);
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
	this.tblTask.setColumnExpandRatio("Samstag", 0.05F);
	this.tblTask.setColumnHeader("Sonntag", "Sonntag");
	this.tblTask.setColumnExpandRatio("Sonntag", 0.05F);

	this.containerFilterComponent.setContainer(this.tblTask.getBeanContainerDataSource(), "kennzeichen", "gewicht",
			"fahrzeugFunktion", "nutzlast", "nummer");
	this.containerFilterComponent.setSearchableProperties("kennzeichen", "fahrzeugFunktion.beschreibung");

	this.button.setSizeUndefined();
	this.horizontalLayout.addComponent(this.button);
	this.horizontalLayout.setComponentAlignment(this.button, Alignment.MIDDLE_CENTER);
	this.btnWeek.setSizeUndefined();
	this.horizontalLayout.addComponent(this.btnWeek);
	this.horizontalLayout.setComponentAlignment(this.btnWeek, Alignment.MIDDLE_CENTER);
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
	this.btnEmployee.setSizeUndefined();
	this.horizontalLayout6.addComponent(this.btnEmployee);
	this.horizontalLayout6.setComponentAlignment(this.btnEmployee, Alignment.MIDDLE_CENTER);
	this.btnTrailer.setSizeUndefined();
	this.horizontalLayout6.addComponent(this.btnTrailer);
	this.horizontalLayout6.setComponentAlignment(this.btnTrailer, Alignment.MIDDLE_CENTER);
	final CustomComponent horizontalLayout6_spacer = new CustomComponent();
	horizontalLayout6_spacer.setSizeFull();
	this.horizontalLayout6.addComponent(horizontalLayout6_spacer);
	this.horizontalLayout6.setExpandRatio(horizontalLayout6_spacer, 1.0F);
	this.comboBox.setSizeUndefined();
	this.horizontalLayout4.addComponent(this.comboBox);
	this.horizontalLayout4.setComponentAlignment(this.comboBox, Alignment.MIDDLE_CENTER);
	this.containerFilterComponent2.setWidth(100, Unit.PERCENTAGE);
	this.containerFilterComponent2.setHeight(-1, Unit.PIXELS);
	this.horizontalLayout4.addComponent(this.containerFilterComponent2);
	this.horizontalLayout4.setComponentAlignment(this.containerFilterComponent2, Alignment.MIDDLE_RIGHT);
	this.horizontalLayout4.setExpandRatio(this.containerFilterComponent2, 100.0F);
	this.gridLayout4.setColumns(1);
	this.gridLayout4.setRows(2);
	this.horizontalLayout4.setSizeUndefined();
	this.gridLayout4.addComponent(this.horizontalLayout4, 0, 0);
	this.tblEmployee.setSizeFull();
	this.gridLayout4.addComponent(this.tblEmployee, 0, 1);
	this.gridLayout4.setRowExpandRatio(1, 100.0F);
	this.gridLayout4.setSizeFull();
	this.pnEmployee.setContent(this.gridLayout4);
	this.containerFilterComponent3.setWidth(100, Unit.PERCENTAGE);
	this.containerFilterComponent3.setHeight(-1, Unit.PIXELS);
	this.horizontalLayout3.addComponent(this.containerFilterComponent3);
	this.horizontalLayout3.setComponentAlignment(this.containerFilterComponent3, Alignment.MIDDLE_CENTER);
	this.horizontalLayout3.setExpandRatio(this.containerFilterComponent3, 100.0F);
	this.gridLayout5.setColumns(1);
	this.gridLayout5.setRows(2);
	this.horizontalLayout3.setSizeUndefined();
	this.gridLayout5.addComponent(this.horizontalLayout3, 0, 0);
	this.tblTrailer.setSizeFull();
	this.gridLayout5.addComponent(this.tblTrailer, 0, 1);
	this.gridLayout5.setRowExpandRatio(1, 100.0F);
	this.gridLayout5.setSizeFull();
	this.pnTrailer.setContent(this.gridLayout5);
	this.pnEmployee.setSizeFull();
	this.horizontalLayout5.addComponent(this.pnEmployee);
	this.horizontalLayout5.setExpandRatio(this.pnEmployee, 10.0F);
	this.pnTrailer.setSizeFull();
	this.horizontalLayout5.addComponent(this.pnTrailer);
	this.horizontalLayout5.setComponentAlignment(this.pnTrailer, Alignment.MIDDLE_CENTER);
	this.horizontalLayout5.setExpandRatio(this.pnTrailer, 10.0F);
	this.gridLayout.setColumns(1);
	this.gridLayout.setRows(6);
	this.horizontalLayout.setWidth(195, Unit.PIXELS);
	this.horizontalLayout.setHeight(40, Unit.PIXELS);
	this.gridLayout.addComponent(this.horizontalLayout, 0, 0);
	this.gridLayout.setComponentAlignment(this.horizontalLayout, Alignment.MIDDLE_CENTER);
	this.horizontalLayout2.setWidth(195, Unit.PIXELS);
	this.horizontalLayout2.setHeight(29, Unit.PIXELS);
	this.gridLayout.addComponent(this.horizontalLayout2, 0, 1);
	this.gridLayout.setComponentAlignment(this.horizontalLayout2, Alignment.MIDDLE_CENTER);
	this.horizontalLayout6.setWidth(100, Unit.PERCENTAGE);
	this.horizontalLayout6.setHeight(50, Unit.PIXELS);
	this.gridLayout.addComponent(this.horizontalLayout6, 0, 2);
	this.horizontalLayout5.setSizeFull();
	this.gridLayout.addComponent(this.horizontalLayout5, 0, 3);
	this.containerFilterComponent.setWidth(100, Unit.PERCENTAGE);
	this.containerFilterComponent.setHeight(-1, Unit.PIXELS);
	this.gridLayout.addComponent(this.containerFilterComponent, 0, 4);
	this.tblTask.setSizeFull();
	this.gridLayout.addComponent(this.tblTask, 0, 5);
	this.gridLayout.setColumnExpandRatio(0, 100.0F);
	this.gridLayout.setRowExpandRatio(3, 100.0F);
	this.gridLayout.setRowExpandRatio(5, 100.0F);
	this.gridLayout.setSizeFull();
	this.setContent(this.gridLayout);
	this.setSizeFull();

	this.button.addClickListener(event -> this.button_buttonClick(event));
	this.btnWeek.addClickListener(event -> this.btnWeek_buttonClick(event));
	this.button2.addClickListener(event -> this.button2_buttonClick(event));
	this.btnEmployee.addClickListener(event -> this.btnEmployee_buttonClick(event));
	this.btnTrailer.addClickListener(event -> this.btnTrailer_buttonClick(event));
	this.comboBox.addValueChangeListener(new Property.ValueChangeListener() {
		@Override
		public void valueChange(Property.ValueChangeEvent event) {
			MainView.this.comboBox_valueChange(event);
		}
	});
	this.tblEmployee.addColumnResizeListener(event -> this.tblEmployee_columnResize(event));
	this.tblTask.addColumnResizeListener(event -> this.tblTask_columnResize(event));
} // </generated-code>

// <generated-code name="variables">
private XdevButton button, btnWeek, button2, btnEmployee, btnTrailer;
private XdevLabel lblWeekInterval, lblYear;
private XdevTable<Anhaenger> tblTrailer;
private XdevHorizontalLayout horizontalLayout, horizontalLayout2, horizontalLayout6, horizontalLayout5,
		horizontalLayout4, horizontalLayout3;
private XdevTable<Fahrzeug> tblTask;
private XdevComboBox<Fahrerfunktion> comboBox;
private XdevPanel pnEmployee, pnTrailer;
private XdevGridLayout gridLayout, gridLayout4, gridLayout5;
private XdevHorizontalSplitPanel horizontalSplitPanel;
private XdevContainerFilterComponent containerFilterComponent2, containerFilterComponent3, containerFilterComponent;
private XdevTable<Fahrer> tblEmployee;
// </generated-code>



































// </generated-code>

public void receiveBroadcast(String message) {
		update();
}

}
