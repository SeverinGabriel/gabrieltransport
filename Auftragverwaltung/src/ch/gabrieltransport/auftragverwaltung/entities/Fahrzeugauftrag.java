package ch.gabrieltransport.auftragverwaltung.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xdev.dal.DAO;
import com.xdev.util.Caption;

import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugauftragDAO;

/**
 * Fahrzeugauftrag
 */
@DAO(daoClass = FahrzeugauftragDAO.class)
@Caption("{%idfahrzeugauftrag}")
@Entity
@Table(name = "fahrzeugauftrag", catalog = "gabrieltransport")
public class Fahrzeugauftrag implements java.io.Serializable {

	private int idfahrzeugauftrag;
	private Auftrag auftrag;
	private Fahrzeug fahrzeug;
	private LocalDateTime vonDatum;
	private LocalDateTime bisDatum;
	private Anhaenger anhaenger;

	public Fahrzeugauftrag() {
	}

	public Fahrzeugauftrag(int idfahrzeugauftrag, Auftrag auftrag, Fahrzeug fahrzeug, LocalDateTime vonDatum, LocalDateTime bisDatum) {
		this.idfahrzeugauftrag = idfahrzeugauftrag;
		this.auftrag = auftrag;
		this.fahrzeug = fahrzeug;
		this.vonDatum = vonDatum;
		this.bisDatum = bisDatum;
	}
	
	public Fahrzeugauftrag(Anhaenger anhaenger, int idfahrzeugauftrag, Auftrag auftrag, Fahrzeug fahrzeug, LocalDateTime vonDatum, LocalDateTime bisDatum) {
		this.idfahrzeugauftrag = idfahrzeugauftrag;
		this.auftrag = auftrag;
		this.fahrzeug = fahrzeug;
		this.vonDatum = vonDatum;
		this.bisDatum = bisDatum;
		this.anhaenger = anhaenger;
	}

	@Caption("Idfahrzeugauftrag")
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idfahrzeugauftrag", unique = true, nullable = false)
	public int getIdfahrzeugauftrag() {
		return this.idfahrzeugauftrag;
	}

	public void setIdfahrzeugauftrag(int idfahrzeugauftrag) {
		this.idfahrzeugauftrag = idfahrzeugauftrag;
	}

	@Caption("Auftrag")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fkAuftrag", nullable = false)
	public Auftrag getAuftrag() {
		return this.auftrag;
	}

	public void setAuftrag(Auftrag auftrag) {
		this.auftrag = auftrag;
	}

	@Caption("Fahrzeug")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fkFahrzeug", nullable = false)
	public Fahrzeug getFahrzeug() {
		return this.fahrzeug;
	}

	public void setFahrzeug(Fahrzeug fahrzeug) {
		this.fahrzeug = fahrzeug;
	}

	@Caption("VonDatum")
	@Column(name = "vonDatum", nullable = false, length = 19)
	public LocalDateTime getVonDatum() {
		return this.vonDatum;
	}

	public void setVonDatum(LocalDateTime vonDatum) {
		this.vonDatum = vonDatum;
	}

	@Caption("BisDatum")
	@Column(name = "bisDatum", nullable = false, length = 19)
	public LocalDateTime getBisDatum() {
		return this.bisDatum;
	}

	public void setBisDatum(LocalDateTime bisDatum) {
		this.bisDatum = bisDatum;
	}
	
	@Caption("Anhaenger")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fkAnhänger", columnDefinition = "INT")
	public Anhaenger getAnhaenger() {
		return this.anhaenger;
	}

	public void setAnhaenger(Anhaenger anhaenger) {
		this.anhaenger = anhaenger;
	}

}
