package ch.gabrieltransport.auftragverwaltung.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xdev.dal.DAO;
import com.xdev.util.Caption;

import ch.gabrieltransport.auftragverwaltung.dal.AuftragDAO;

/**
 * Auftrag
 */
@DAO(daoClass = AuftragDAO.class)
@Caption("{%bezeichung}")
@Entity
@Table(name = "auftrag", catalog = "gabrieltransport")
public class Auftrag implements java.io.Serializable {

	private int idAuftrag;
	private String bezeichung;
	private String beschreibung;
	private LocalDateTime vonDatum;
	private LocalDateTime bisDatum;
	private Set<Fahrerauftrag> fahrerauftrags = new HashSet<Fahrerauftrag>(0);
	private Set<Fahrzeugauftrag> fahrzeugauftrags = new HashSet<Fahrzeugauftrag>(0);

	public Auftrag() {
	}

	public Auftrag(String bezeichung) {
		this.bezeichung = bezeichung;
	}

	public Auftrag(String bezeichung, String beschreibung, LocalDateTime vonDatum, LocalDateTime bisDatum, Set<Fahrerauftrag> fahrerauftrags,
			Set<Fahrzeugauftrag> fahrzeugauftrags) {
		this.bezeichung = bezeichung;
		this.beschreibung = beschreibung;
		this.vonDatum = vonDatum;
		this.bisDatum = bisDatum;
		this.fahrerauftrags = fahrerauftrags;
		this.fahrzeugauftrags = fahrzeugauftrags;
	}

	@Caption("IdAuftrag")
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idAuftrag", unique = true, nullable = false)
	public int getIdAuftrag() {
		return this.idAuftrag;
	}

	public void setIdAuftrag(int idAuftrag) {
		this.idAuftrag = idAuftrag;
	}

	@Caption("Bezeichung")
	@Column(name = "Bezeichung", nullable = false)
	public String getBezeichung() {
		return this.bezeichung;
	}

	public void setBezeichung(String bezeichung) {
		this.bezeichung = bezeichung;
	}
	
	@Caption("Beschreibung")
	@Column(name = "Beschreibung", nullable = true)
	public String getBeschreibung() {
		return this.beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	@Caption("VonDatum")
	@Column(name = "VonDatum", length = 19)
	public LocalDateTime getVonDatum() {
		return this.vonDatum;
	}

	public void setVonDatum(LocalDateTime vonDatum) {
		this.vonDatum = vonDatum;
	}

	@Caption("BisDatum")
	@Column(name = "BisDatum", length = 19)
	public LocalDateTime getBisDatum() {
		return this.bisDatum;
	}

	public void setBisDatum(LocalDateTime bisDatum) {
		this.bisDatum = bisDatum;
	}

	@Caption("Fahrerauftrags")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "auftrag", cascade = CascadeType.ALL)
	public Set<Fahrerauftrag> getFahrerauftrags() {
		return this.fahrerauftrags;
	}

	public void setFahrerauftrags(Set<Fahrerauftrag> fahrerauftrags) {
		this.fahrerauftrags = fahrerauftrags;
	}

	@Caption("Fahrzeugauftrags")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "auftrag", cascade = CascadeType.ALL)
	public Set<Fahrzeugauftrag> getFahrzeugauftrags() {
		return this.fahrzeugauftrags;
	}

	public void setFahrzeugauftrags(Set<Fahrzeugauftrag> fahrzeugauftrags) {
		this.fahrzeugauftrags = fahrzeugauftrags;
	}

}
