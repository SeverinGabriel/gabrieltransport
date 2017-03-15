package ch.gabrieltransport.auftragverwaltung.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import com.xdev.dal.DAO;
import com.xdev.util.Caption;

import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugDAO;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Fahrzeug
 */
@DAO(daoClass = FahrzeugDAO.class)
@Caption("{%kennzeichen}")
@Entity
@Table(name = "fahrzeug", catalog = "gabrieltransport")
public class Fahrzeug implements java.io.Serializable {

	private int idfahrzeug;
	private Anhaengertyp anhaengertyp;
	private FahrzeugFunktion fahrzeugFunktion;
	private String kennzeichen;
	private Integer nutzlast;
	private int nummer;
	private Boolean anhaenger;

	public Fahrzeug() {
	}

	public Fahrzeug(String kennzeichen, int nummer) {
		this.kennzeichen = kennzeichen;
		this.nummer = nummer;
	}

	public Fahrzeug(Anhaengertyp anhaengertyp, FahrzeugFunktion fahrzeugFunktion, String kennzeichen, Integer nutzlast,
			int nummer, Boolean anhaenger) {
		this.anhaengertyp = anhaengertyp;
		this.fahrzeugFunktion = fahrzeugFunktion;
		this.kennzeichen = kennzeichen;
		this.nutzlast = nutzlast;
		this.nummer = nummer;
		this.anhaenger = anhaenger;
	}

	@Caption("Idfahrzeug")
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idfahrzeug", unique = true, nullable = false, columnDefinition = "INT")
	public int getIdfahrzeug() {
		return this.idfahrzeug;
	}

	public void setIdfahrzeug(int idfahrzeug) {
		this.idfahrzeug = idfahrzeug;
	}

	@Caption("Anhaengertyp")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AnhaengerTyp", columnDefinition = "INT")
	public Anhaengertyp getAnhaengertyp() {
		return this.anhaengertyp;
	}

	public void setAnhaengertyp(Anhaengertyp anhaengertyp) {
		this.anhaengertyp = anhaengertyp;
	}

	@Caption("FahrzeugFunktion")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Funktion", columnDefinition = "INT")
	public FahrzeugFunktion getFahrzeugFunktion() {
		return this.fahrzeugFunktion;
	}

	public void setFahrzeugFunktion(FahrzeugFunktion fahrzeugFunktion) {
		this.fahrzeugFunktion = fahrzeugFunktion;
	}

	@Caption("Kennzeichen")
	@Column(name = "Kennzeichen", nullable = false, columnDefinition = "VARCHAR", length = 45)
	public String getKennzeichen() {
		return this.kennzeichen;
	}

	public void setKennzeichen(String kennzeichen) {
		this.kennzeichen = kennzeichen;
	}

	@Caption("Nutzlast")
	@Column(name = "Nutzlast", columnDefinition = "INT")
	public Integer getNutzlast() {
		return this.nutzlast;
	}

	public void setNutzlast(Integer nutzlast) {
		this.nutzlast = nutzlast;
	}

	@Caption("Nummer")
	@Column(name = "Nummer", nullable = false, columnDefinition = "INT")
	public int getNummer() {
		return this.nummer;
	}

	public void setNummer(int nummer) {
		this.nummer = nummer;
	}

	@Caption("Anhaenger")
	@Column(name = "Anhaenger", columnDefinition = "BIT")
	public Boolean getAnhaenger() {
		return this.anhaenger;
	}

	public void setAnhaenger(Boolean anhaenger) {
		this.anhaenger = anhaenger;
	}

}