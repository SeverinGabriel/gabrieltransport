package ch.gabrieltransport.auftragverwaltung.entities;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xdev.dal.DAO;
import com.xdev.util.Caption;

import ch.gabrieltransport.auftragverwaltung.dal.FahrerauftragDAO;

/**
 * Fahrerauftrag
 */
@DAO(daoClass = FahrerauftragDAO.class)
@Caption("{%idfahrerauftrag}")
@Entity
@Table(name = "fahrerauftrag", catalog = "gabrieltransport")
public class Fahrerauftrag implements java.io.Serializable {

	private int idfahrerauftrag;
	private Auftrag auftrag;
	private Fahrer fahrer;
	private LocalDateTime vonDatum;
	private LocalDateTime bisDatum;

	public Fahrerauftrag() {
	}

	public Fahrerauftrag(Auftrag auftrag, Fahrer fahrer, LocalDateTime vonDatum, LocalDateTime bisDatum) {
		this.auftrag = auftrag;
		this.fahrer = fahrer;
		this.vonDatum = vonDatum;
		this.bisDatum = bisDatum;
	}

	@Caption("Idfahrerauftrag")
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idfahrerauftrag", unique = true, nullable = false)
	public int getIdfahrerauftrag() {
		return this.idfahrerauftrag;
	}

	public void setIdfahrerauftrag(int idfahrerauftrag) {
		this.idfahrerauftrag = idfahrerauftrag;
	}

	@Caption("Auftrag")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fkauftrag", nullable = false)
	public Auftrag getAuftrag() {
		return this.auftrag;
	}

	public void setAuftrag(Auftrag auftrag) {
		this.auftrag = auftrag;
	}

	@Caption("Fahrer")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fkFahrer", nullable = false)
	public Fahrer getFahrer() {
		return this.fahrer;
	}

	public void setFahrer(Fahrer fahrer) {
		this.fahrer = fahrer;
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

}
