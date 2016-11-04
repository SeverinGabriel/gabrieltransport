package ch.gabrieltransport.auftragverwaltung.ui;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Window;
import com.xdev.dal.DAOs;
import com.xdev.ui.XdevAbsoluteLayout;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevLabel;
import com.xdev.ui.XdevPopupDateField;
import com.xdev.ui.XdevTextField;
import com.xdev.ui.XdevVerticalLayout;
import com.xdev.ui.XdevView;
import com.xdev.ui.entitycomponent.combobox.XdevComboBox;
import com.xdev.ui.entitycomponent.listselect.XdevOptionGroup;
import com.xdev.ui.entitycomponent.listselect.XdevTwinColSelect;

import ch.gabrieltransport.auftragverwaltung.business.facade.AuftragServiceFacade;
import ch.gabrieltransport.auftragverwaltung.dal.FahrerDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;

public class taskDetail extends XdevView {

	public static class Callback{
		public void onDialogResult(boolean result){}
	}
	/**
	 * 
	 */
	private Auftrag currentTask = new Auftrag();
	private Callback callback;
	private AuftragServiceFacade auftragServiceFacade = new AuftragServiceFacade();
	public taskDetail() {
		super();
		this.initUI();
		optionGroup.addItem("Ganztägig");
		optionGroup.addItem("Vormittag");
		optionGroup.addItem("Nachmittag");
		optionGroup.addItem("Spezifische Zeit");
		
		
		
	}
	public taskDetail(Auftrag auftrag) {
		this();
		currentTask = auftrag;
		BeanItem<Auftrag> test = new BeanItem<Auftrag>(currentTask);
		txtDescription.setPropertyDataSource(test.getItemProperty("bezeichung"));
		
	}
	public taskDetail(LocalDateTime ldt, Fahrzeug fz, Callback callback) {
		
		this();
		this.callback = callback;
		Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		fromDate.setValue(date);
		untilDate.setValue(date);
		cmbVehicle.select(fz.getIdfahrzeug());
	}

	/**
	 * Event handler delegate method for the {@link XdevOptionGroup}
	 * {@link #optionGroup}.
	 *
	 * @see Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void optionGroup_valueChange(Property.ValueChangeEvent event) {
		if (event.getProperty().getValue() == "Spezifische Zeit"){
			gridLayout2.setVisible(true);
		}
		else {
			gridLayout2.setVisible(false);
		}
	}

	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #btnCancel}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void btnCancel_buttonClick(Button.ClickEvent event) {
		((Window)this.getParent()).close();
	}

	/**
	 * Event handler delegate method for the {@link XdevComboBox}
	 * {@link #cmbVehicle}.
	 *
	 * @see Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void cmbVehicle_valueChange(Property.ValueChangeEvent event) {
		lblWeight.setValue( cmbVehicle.getSelectedItem().getBean().getGewicht());
		lblSign.setValue(cmbVehicle.getSelectedItem().getBean().getKennzeichen());
	}
	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #button3}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button3_buttonClick(Button.ClickEvent event) {
		auftragServiceFacade.deleteAuftrag(currentTask);
		
	}
	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #btnSave}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void btnSave_buttonClick(Button.ClickEvent event) {
		currentTask.setBezeichung(txtDescription.getValue());
		LocalDateTime ldt = LocalDateTime.ofInstant(untilDate.getValue().toInstant(), ZoneId.systemDefault());
		currentTask.setBisDatum(ldt);
		ldt = LocalDateTime.ofInstant(fromDate.getValue().toInstant(), ZoneId.systemDefault());
		currentTask.setVonDatum(ldt);
		Set<Fahrzeugauftrag> fahrzeugauftraege = currentTask.getFahrzeugauftrags();
		
		
		auftragServiceFacade.persistAuftrag(currentTask, cmbVehicle.getSelectedItem().getBean(), null);
		callback.onDialogResult(true);
	}
	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated
	 * by the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.absoluteLayout = new XdevAbsoluteLayout();
		this.verticalLayout = new XdevVerticalLayout();
		this.horizontalLayout4 = new XdevHorizontalLayout();
		this.lblMission = new XdevLabel();
		this.button3 = new XdevButton();
		this.horizontalLayout = new XdevHorizontalLayout();
		this.lblDescription = new XdevLabel();
		this.txtDescription = new XdevTextField();
		this.gridLayout = new XdevGridLayout();
		this.label3 = new XdevLabel();
		this.fromDate = new XdevPopupDateField();
		this.label4 = new XdevLabel();
		this.untilDate = new XdevPopupDateField();
		this.horizontalLayout3 = new XdevHorizontalLayout();
		this.optionGroup = new XdevOptionGroup<>();
		this.gridLayout2 = new XdevGridLayout();
		this.label10 = new XdevLabel();
		this.label8 = new XdevLabel();
		this.lblTimeFrom = new XdevTextField();
		this.label9 = new XdevLabel();
		this.txtTimeUntil = new XdevTextField();
		this.lblVehicle = new XdevLabel();
		this.horizontalLayout2 = new XdevHorizontalLayout();
		this.cmbVehicle = new XdevComboBox<>();
		this.lblSign = new XdevLabel();
		this.lblWeight = new XdevLabel();
		this.lblDriver = new XdevLabel();
		this.horizontalLayout5 = new XdevHorizontalLayout();
		this.tsDriver = new XdevTwinColSelect<>();
		this.btnSave = new XdevButton();
		this.btnCancel = new XdevButton();
	
		this.verticalLayout.setMargin(new MarginInfo(true, false, false, false));
		this.lblMission.setValue("Auftrag");
		this.button3.setCaption("Delete");
		this.horizontalLayout.setMargin(new MarginInfo(false, true, true, true));
		this.lblDescription.setValue("Bezeichnung");
		this.gridLayout.setMargin(new MarginInfo(false));
		this.label3.setValue("Von");
		this.label4.setValue("Bis");
		this.horizontalLayout3.setMargin(new MarginInfo(false, true, true, true));
		this.gridLayout2.setMargin(new MarginInfo(false));
		this.gridLayout2.setVisible(false);
		this.label10.setValue("Zeit (hh:mm)");
		this.label8.setValue("Von");
		this.label9.setValue("Bis");
		this.lblVehicle.setValue("Fahrzeug");
		this.horizontalLayout2.setMargin(new MarginInfo(false, true, true, true));
		this.cmbVehicle.setItemCaptionFromAnnotation(false);
		this.cmbVehicle.setContainerDataSource(Fahrzeug.class);
		this.cmbVehicle.setItemCaptionPropertyId("idfahrzeug");
		this.lblSign.setValue("Kennzeichen");
		this.lblWeight.setValue("Gewicht");
		this.lblDriver.setValue("Fahrer");
		this.horizontalLayout5.setMargin(new MarginInfo(false, true, true, true));
		this.tsDriver.setItemCaptionFromAnnotation(false);
		this.tsDriver.setRightColumnCaption("zugewiesen");
		this.tsDriver.setLeftColumnCaption("verfügbar");
		this.tsDriver.setRows(4);
		this.tsDriver.setContainerDataSource(Fahrer.class, DAOs.get(FahrerDAO.class).findAll());
		this.tsDriver.setItemCaptionPropertyId("nachname");
		this.btnSave.setCaption("OK");
		this.btnCancel.setCaption("Abbrechen");
	
		this.lblMission.setWidth(100, Unit.PERCENTAGE);
		this.lblMission.setHeight(-1, Unit.PIXELS);
		this.horizontalLayout4.addComponent(this.lblMission);
		this.horizontalLayout4.setComponentAlignment(this.lblMission, Alignment.BOTTOM_LEFT);
		this.horizontalLayout4.setExpandRatio(this.lblMission, 0.6F);
		this.button3.setWidth(-1, Unit.PIXELS);
		this.button3.setHeight(30, Unit.PIXELS);
		this.horizontalLayout4.addComponent(this.button3);
		this.horizontalLayout4.setComponentAlignment(this.button3, Alignment.BOTTOM_LEFT);
		this.gridLayout.setColumns(2);
		this.gridLayout.setRows(3);
		this.label3.setSizeUndefined();
		this.gridLayout.addComponent(this.label3, 0, 0);
		this.fromDate.setWidth(100, Unit.PERCENTAGE);
		this.fromDate.setHeight(-1, Unit.PIXELS);
		this.gridLayout.addComponent(this.fromDate, 1, 0);
		this.label4.setSizeUndefined();
		this.gridLayout.addComponent(this.label4, 0, 1);
		this.untilDate.setWidth(100, Unit.PERCENTAGE);
		this.untilDate.setHeight(-1, Unit.PIXELS);
		this.gridLayout.addComponent(this.untilDate, 1, 1);
		this.gridLayout.setColumnExpandRatio(1, 0.1F);
		CustomComponent gridLayout_vSpacer = new CustomComponent();
		gridLayout_vSpacer.setSizeFull();
		this.gridLayout.addComponent(gridLayout_vSpacer, 0, 2, 1, 2);
		this.gridLayout.setRowExpandRatio(2, 1.0F);
		this.lblDescription.setSizeUndefined();
		this.horizontalLayout.addComponent(this.lblDescription);
		this.horizontalLayout.setComponentAlignment(this.lblDescription, Alignment.TOP_CENTER);
		this.txtDescription.setWidth(100, Unit.PERCENTAGE);
		this.txtDescription.setHeight(-1, Unit.PIXELS);
		this.horizontalLayout.addComponent(this.txtDescription);
		this.horizontalLayout.setComponentAlignment(this.txtDescription, Alignment.TOP_CENTER);
		this.horizontalLayout.setExpandRatio(this.txtDescription, 0.1F);
		this.gridLayout.setSizeFull();
		this.horizontalLayout.addComponent(this.gridLayout);
		this.horizontalLayout.setComponentAlignment(this.gridLayout, Alignment.MIDDLE_RIGHT);
		this.horizontalLayout.setExpandRatio(this.gridLayout, 0.1F);
		this.gridLayout2.setColumns(2);
		this.gridLayout2.setRows(4);
		this.label10.setSizeUndefined();
		this.gridLayout2.addComponent(this.label10, 0, 0);
		this.label8.setSizeUndefined();
		this.gridLayout2.addComponent(this.label8, 0, 1);
		this.lblTimeFrom.setWidth(70, Unit.PERCENTAGE);
		this.lblTimeFrom.setHeight(-1, Unit.PIXELS);
		this.gridLayout2.addComponent(this.lblTimeFrom, 1, 1);
		this.label9.setSizeUndefined();
		this.gridLayout2.addComponent(this.label9, 0, 2);
		this.txtTimeUntil.setSizeUndefined();
		this.gridLayout2.addComponent(this.txtTimeUntil, 1, 2);
		this.gridLayout2.setColumnExpandRatio(1, 0.1F);
		CustomComponent gridLayout2_vSpacer = new CustomComponent();
		gridLayout2_vSpacer.setSizeFull();
		this.gridLayout2.addComponent(gridLayout2_vSpacer, 0, 3, 1, 3);
		this.gridLayout2.setRowExpandRatio(3, 1.0F);
		this.optionGroup.setSizeUndefined();
		this.horizontalLayout3.addComponent(this.optionGroup);
		this.horizontalLayout3.setExpandRatio(this.optionGroup, 0.2F);
		this.gridLayout2.setWidth(100, Unit.PERCENTAGE);
		this.gridLayout2.setHeight(90, Unit.PERCENTAGE);
		this.horizontalLayout3.addComponent(this.gridLayout2);
		this.horizontalLayout3.setExpandRatio(this.gridLayout2, 0.3F);
		this.cmbVehicle.setWidth(100, Unit.PERCENTAGE);
		this.cmbVehicle.setHeight(-1, Unit.PIXELS);
		this.horizontalLayout2.addComponent(this.cmbVehicle);
		this.horizontalLayout2.setComponentAlignment(this.cmbVehicle, Alignment.MIDDLE_LEFT);
		this.horizontalLayout2.setExpandRatio(this.cmbVehicle, 0.1F);
		this.lblSign.setSizeUndefined();
		this.horizontalLayout2.addComponent(this.lblSign);
		this.horizontalLayout2.setComponentAlignment(this.lblSign, Alignment.MIDDLE_CENTER);
		this.horizontalLayout2.setExpandRatio(this.lblSign, 0.1F);
		this.lblWeight.setSizeUndefined();
		this.horizontalLayout2.addComponent(this.lblWeight);
		this.horizontalLayout2.setComponentAlignment(this.lblWeight, Alignment.MIDDLE_CENTER);
		this.horizontalLayout2.setExpandRatio(this.lblWeight, 0.1F);
		this.tsDriver.setSizeUndefined();
		this.horizontalLayout5.addComponent(this.tsDriver);
		this.horizontalLayout5.setComponentAlignment(this.tsDriver, Alignment.MIDDLE_LEFT);
		CustomComponent horizontalLayout5_spacer = new CustomComponent();
		horizontalLayout5_spacer.setSizeFull();
		this.horizontalLayout5.addComponent(horizontalLayout5_spacer);
		this.horizontalLayout5.setExpandRatio(horizontalLayout5_spacer, 1.0F);
		this.horizontalLayout4.setWidth(100, Unit.PERCENTAGE);
		this.horizontalLayout4.setHeight(100, Unit.PIXELS);
		this.verticalLayout.addComponent(this.horizontalLayout4);
		this.verticalLayout.setComponentAlignment(this.horizontalLayout4, Alignment.MIDDLE_CENTER);
		this.horizontalLayout.setWidth(100, Unit.PERCENTAGE);
		this.horizontalLayout.setHeight(-1, Unit.PIXELS);
		this.verticalLayout.addComponent(this.horizontalLayout);
		this.verticalLayout.setComponentAlignment(this.horizontalLayout, Alignment.MIDDLE_CENTER);
		this.verticalLayout.setExpandRatio(this.horizontalLayout, 0.1F);
		this.horizontalLayout3.setWidth(100, Unit.PERCENTAGE);
		this.horizontalLayout3.setHeight(244, Unit.PIXELS);
		this.verticalLayout.addComponent(this.horizontalLayout3);
		this.verticalLayout.setComponentAlignment(this.horizontalLayout3, Alignment.MIDDLE_CENTER);
		this.verticalLayout.setExpandRatio(this.horizontalLayout3, 0.1F);
		this.lblVehicle.setSizeUndefined();
		this.verticalLayout.addComponent(this.lblVehicle);
		this.verticalLayout.setComponentAlignment(this.lblVehicle, Alignment.BOTTOM_LEFT);
		this.verticalLayout.setExpandRatio(this.lblVehicle, 0.03F);
		this.horizontalLayout2.setWidth(100, Unit.PERCENTAGE);
		this.horizontalLayout2.setHeight(-1, Unit.PIXELS);
		this.verticalLayout.addComponent(this.horizontalLayout2);
		this.verticalLayout.setComponentAlignment(this.horizontalLayout2, Alignment.MIDDLE_CENTER);
		this.lblDriver.setSizeUndefined();
		this.verticalLayout.addComponent(this.lblDriver);
		this.verticalLayout.setComponentAlignment(this.lblDriver, Alignment.BOTTOM_LEFT);
		this.verticalLayout.setExpandRatio(this.lblDriver, 0.03F);
		this.horizontalLayout5.setWidth(392, Unit.PIXELS);
		this.horizontalLayout5.setHeight(181, Unit.PIXELS);
		this.verticalLayout.addComponent(this.horizontalLayout5);
		this.verticalLayout.setComponentAlignment(this.horizontalLayout5, Alignment.MIDDLE_LEFT);
		this.verticalLayout.setWidth(543, Unit.PIXELS);
		this.verticalLayout.setHeight(886, Unit.PIXELS);
		this.absoluteLayout.addComponent(this.verticalLayout, "left:29px; top:0px");
		this.absoluteLayout.addComponent(this.btnSave, "left:371px; top:920px");
		this.absoluteLayout.addComponent(this.btnCancel, "left:443px; top:920px");
		this.absoluteLayout.setWidth(600, Unit.PIXELS);
		this.absoluteLayout.setHeight(1000, Unit.PIXELS);
		this.setContent(this.absoluteLayout);
		this.setWidth(600, Unit.PIXELS);
		this.setHeight(1000, Unit.PIXELS);
	
		button3.addClickListener(event -> this.button3_buttonClick(event));
		optionGroup.addValueChangeListener(event -> this.optionGroup_valueChange(event));
		this.cmbVehicle.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				taskDetail.this.cmbVehicle_valueChange(event);
			}
		});
		btnSave.addClickListener(event -> this.btnSave_buttonClick(event));
		btnCancel.addClickListener(event -> this.btnCancel_buttonClick(event));
	} // </generated-code>
	// <generated-code name="variables">
	private XdevPopupDateField fromDate, untilDate;
	private XdevHorizontalLayout horizontalLayout4, horizontalLayout, horizontalLayout3, horizontalLayout2,
			horizontalLayout5;
	private XdevLabel lblMission, lblDescription, label3, label4, label10, label8, label9, lblVehicle, lblSign, lblWeight,
			lblDriver;
	private XdevVerticalLayout verticalLayout;
	private XdevComboBox<Fahrzeug> cmbVehicle;
	private XdevTextField txtDescription, lblTimeFrom, txtTimeUntil;
	private XdevTwinColSelect<Fahrer> tsDriver;
	private XdevAbsoluteLayout absoluteLayout;
	private XdevButton button3, btnSave, btnCancel;
	private XdevOptionGroup<String> optionGroup;
	private XdevGridLayout gridLayout, gridLayout2; // </generated-code>


}
