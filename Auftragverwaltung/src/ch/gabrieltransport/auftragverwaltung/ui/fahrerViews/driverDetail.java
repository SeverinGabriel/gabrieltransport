package ch.gabrieltransport.auftragverwaltung.ui.fahrerViews;

import ch.gabrieltransport.auftragverwaltung.business.facade.FahrerAuftragServiceFacade;
import ch.gabrieltransport.auftragverwaltung.business.refresher.Broadcaster;
import ch.gabrieltransport.auftragverwaltung.dal.FahrerauftragDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag_;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer_;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerauftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerauftrag_;
import ch.gabrieltransport.auftragverwaltung.ui.fahrerViews.DriverTaskDeleteColumn.Generator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional.TxType;

import com.vaadin.data.Property;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevPopupDateField;
import com.xdev.ui.XdevTextArea;
import com.xdev.ui.XdevTextField;
import com.xdev.ui.XdevView;
import com.xdev.ui.entitycomponent.combobox.XdevComboBox;
import com.xdev.ui.entitycomponent.table.XdevTable;
import com.xdev.ui.masterdetail.MasterDetail;
import com.xdev.ui.util.NestedProperty;

public class driverDetail extends XdevView {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	FahrerauftragDAO faDAO = new FahrerauftragDAO();
	FahrerAuftragServiceFacade faFacade = new FahrerAuftragServiceFacade();
	/**
	 * 
	 */
	public driverDetail(Fahrer driver, LocalDateTime weekStart) {
		super();
		this.initUI();
		dateFrom.setValue(Date.from(weekStart.atZone(ZoneId.systemDefault()).toInstant()));
		dateUntil.setValue(Date.from(weekStart.plusDays(6).atZone(ZoneId.systemDefault()).toInstant()));
		comboBox.select(driver.getIdfahrer());
		cmbAbsence.addItems("Ferien", "Kompensation","Krankheit", "Militär");
		cmbAbsence.select("Ferien");
	}

	/**
	 * Event handler delegate method for the {@link XdevComboBox} {@link #comboBox}.
	 *
	 * @see Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void comboBox_valueChange(Property.ValueChangeEvent event) {
		fillTable();
	}
	
	private void fillTable(){
		if (!dateFrom.isEmpty() && !dateUntil.isEmpty()) {
			LocalDate ldFrom = dateFrom.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate ldUntil = dateUntil.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (comboBox.getSelectedItem() != null) {
				Fahrer driver = comboBox.getSelectedItem().getBean();
				if(driver != null && (ldFrom.isBefore(ldUntil) || ldFrom.isEqual(ldUntil))){
					List<Fahrerauftrag> tasks = faDAO.findAuftragebetween(ldFrom, ldUntil, driver);
					tblDriverTask.getBeanContainerDataSource().removeAll();
					tblDriverTask.getBeanContainerDataSource().addAll(tasks);
				}
			}
			
		}
	}

	/**
	 * Event handler delegate method for the {@link XdevPopupDateField}
	 * {@link #dateFrom}.
	 *
	 * @see Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void dateFrom_valueChange(Property.ValueChangeEvent event) {
		fillTable();
	}

	/**
	 * Event handler delegate method for the {@link XdevPopupDateField}
	 * {@link #dateUntil}.
	 *
	 * @see Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void dateUntil_valueChange(Property.ValueChangeEvent event) {
		fillTable();
	}

	
	private LocalTime parseTextfield(XdevTextField textfield){
		try{
			return LocalTime.parse(textfield.getValue());
		}catch(Exception e){
			new Notification("Eingabefehler",
				    "<br/>Zeitformat nicht korrekt",
				    Notification.TYPE_ERROR_MESSAGE, true)
				    .show(Page.getCurrent());
			return null;
		}
	}
	
	private LocalDate parseDateField(XdevPopupDateField dateField){
		if(dateField.getValue() != null){
			return dateField.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		return null;
	}
	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #button}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button_buttonClick(Button.ClickEvent event) {
		LocalTime ltStart = parseTextfield(txtTimeFrom);
		LocalTime ltEnd = parseTextfield(txtTimeUntil);
		if(ltStart != null && ltEnd != null){
			LocalDate ldStart = parseDateField(holidayDateFrom);
			LocalDate ldEnd = parseDateField(holidayDateUntil);
			if(ldStart != null && ldEnd != null){
				LocalDateTime ldtStart = LocalDateTime.of(ldStart, ltStart);
				LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);
				String test = "null";
				if(cmbAbsence.getValue() != null){
					test = (String) cmbAbsence.getValue();
				}
				Fahrerauftrag fa = faFacade.persistHoliday(ldtStart, ldtEnd, comboBox.getSelectedItem().getBean(), test);
				tblDriverTask.getBeanContainerDataSource().addBean(fa);
			}
		}
		Broadcaster.broadcast("DRIVER");
	}

	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated by
	 * the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.gridLayout = new XdevGridLayout();
		this.horizontalLayout = new XdevHorizontalLayout();
		this.comboBox = new XdevComboBox<>();
		this.dateFrom = new XdevPopupDateField();
		this.dateUntil = new XdevPopupDateField();
		this.tblDriverTask = new XdevTable<>();
		this.horizontalLayout2 = new XdevHorizontalLayout();
		this.cmbAbsence = new XdevComboBox<>();
		this.holidayDateFrom = new XdevPopupDateField();
		this.txtTimeFrom = new XdevTextField();
		this.holidayDateUntil = new XdevPopupDateField();
		this.txtTimeUntil = new XdevTextField();
		this.button = new XdevButton();
	
		this.comboBox.setItemCaptionFromAnnotation(false);
		this.comboBox.setContainerDataSource(Fahrer.class);
		this.comboBox.setItemCaptionPropertyId(Fahrer_.name.getName());
		this.tblDriverTask.setContainerDataSource(Fahrerauftrag.class, false,
				NestedProperty.of(Fahrerauftrag_.auftrag, Auftrag_.bezeichung));
		this.tblDriverTask.addGeneratedColumn("generated", new DriverTaskDeleteColumn.Generator());
		this.tblDriverTask.setVisibleColumns(NestedProperty.path(Fahrerauftrag_.auftrag, Auftrag_.bezeichung),
				Fahrerauftrag_.vonDatum.getName(), Fahrerauftrag_.bisDatum.getName(), "generated");
		this.tblDriverTask.setColumnHeader("auftrag.bezeichung", "Auftrag");
		this.tblDriverTask.setColumnHeader("vonDatum", "Von");
		this.tblDriverTask.setColumnHeader("bisDatum", "Bis");
		this.tblDriverTask.setColumnHeader("generated", " ");
		this.horizontalLayout2.setCaption("Abwesenheit erfassen");
		this.horizontalLayout2.setMargin(new MarginInfo(false));
		this.cmbAbsence.setRequired(true);
		this.cmbAbsence.setFilteringMode(FilteringMode.OFF);
		this.cmbAbsence.setNullSelectionAllowed(false);
		this.cmbAbsence.setAutoQueryData(false);
		this.cmbAbsence.setNewItemsAllowed(true);
		this.holidayDateFrom.setCaption("von");
		this.txtTimeFrom.setColumns(5);
		this.txtTimeFrom.setCaption("Zeit von (hh:mm)");
		this.txtTimeFrom.setValue("00:00");
		this.holidayDateUntil.setCaption("bis");
		this.txtTimeUntil.setColumns(5);
		this.txtTimeUntil.setCaption("Zeit bis (hh:mm)");
		this.txtTimeUntil.setValue("00:00");
		this.button.setCaption("Erfassen");
	
		this.comboBox.setSizeUndefined();
		this.horizontalLayout.addComponent(this.comboBox);
		this.dateFrom.setSizeUndefined();
		this.horizontalLayout.addComponent(this.dateFrom);
		this.horizontalLayout.setComponentAlignment(this.dateFrom, Alignment.MIDDLE_CENTER);
		this.dateUntil.setSizeUndefined();
		this.horizontalLayout.addComponent(this.dateUntil);
		this.horizontalLayout.setComponentAlignment(this.dateUntil, Alignment.MIDDLE_CENTER);
		final CustomComponent horizontalLayout_spacer = new CustomComponent();
		horizontalLayout_spacer.setSizeFull();
		this.horizontalLayout.addComponent(horizontalLayout_spacer);
		this.horizontalLayout.setExpandRatio(horizontalLayout_spacer, 1.0F);
		this.cmbAbsence.setSizeUndefined();
		this.horizontalLayout2.addComponent(this.cmbAbsence);
		this.horizontalLayout2.setComponentAlignment(this.cmbAbsence, Alignment.BOTTOM_CENTER);
		this.holidayDateFrom.setSizeUndefined();
		this.horizontalLayout2.addComponent(this.holidayDateFrom);
		this.horizontalLayout2.setComponentAlignment(this.holidayDateFrom, Alignment.BOTTOM_CENTER);
		this.txtTimeFrom.setSizeUndefined();
		this.horizontalLayout2.addComponent(this.txtTimeFrom);
		this.horizontalLayout2.setComponentAlignment(this.txtTimeFrom, Alignment.BOTTOM_CENTER);
		this.holidayDateUntil.setSizeUndefined();
		this.horizontalLayout2.addComponent(this.holidayDateUntil);
		this.horizontalLayout2.setComponentAlignment(this.holidayDateUntil, Alignment.BOTTOM_CENTER);
		this.txtTimeUntil.setSizeUndefined();
		this.horizontalLayout2.addComponent(this.txtTimeUntil);
		this.horizontalLayout2.setComponentAlignment(this.txtTimeUntil, Alignment.BOTTOM_CENTER);
		this.button.setSizeUndefined();
		this.horizontalLayout2.addComponent(this.button);
		this.horizontalLayout2.setComponentAlignment(this.button, Alignment.BOTTOM_CENTER);
		final CustomComponent horizontalLayout2_spacer = new CustomComponent();
		horizontalLayout2_spacer.setSizeFull();
		this.horizontalLayout2.addComponent(horizontalLayout2_spacer);
		this.horizontalLayout2.setExpandRatio(horizontalLayout2_spacer, 1.0F);
		this.gridLayout.setColumns(1);
		this.gridLayout.setRows(3);
		this.horizontalLayout.setWidth(100, Unit.PERCENTAGE);
		this.horizontalLayout.setHeight(100, Unit.PIXELS);
		this.gridLayout.addComponent(this.horizontalLayout, 0, 0);
		this.tblDriverTask.setSizeFull();
		this.gridLayout.addComponent(this.tblDriverTask, 0, 1);
		this.horizontalLayout2.setSizeFull();
		this.gridLayout.addComponent(this.horizontalLayout2, 0, 2);
		this.gridLayout.setColumnExpandRatio(0, 10.0F);
		this.gridLayout.setRowExpandRatio(1, 100.0F);
		this.gridLayout.setRowExpandRatio(2, 12.0F);
		this.gridLayout.setSizeFull();
		this.setContent(this.gridLayout);
		this.setSizeFull();
	
		this.comboBox.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				driverDetail.this.comboBox_valueChange(event);
			}
		});
		this.dateFrom.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				driverDetail.this.dateFrom_valueChange(event);
			}
		});
		this.dateUntil.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				driverDetail.this.dateUntil_valueChange(event);
			}
		});
		this.button.addClickListener(event -> this.button_buttonClick(event));
	} // </generated-code>

	// <generated-code name="variables">
	private XdevButton button;
	private XdevTable<Fahrerauftrag> tblDriverTask;
	private XdevHorizontalLayout horizontalLayout, horizontalLayout2;
	private XdevPopupDateField dateFrom, dateUntil, holidayDateFrom, holidayDateUntil;
	private XdevComboBox<String> cmbAbsence;
	private XdevGridLayout gridLayout;
	private XdevTextField txtTimeFrom, txtTimeUntil;
	private XdevComboBox<Fahrer> comboBox;
	// </generated-code>

}
