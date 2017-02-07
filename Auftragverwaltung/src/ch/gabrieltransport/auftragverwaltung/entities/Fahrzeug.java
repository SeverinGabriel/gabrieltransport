package ch.gabrieltransport.auftragverwaltung.entities;

import com.xdev.util.Caption;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Fahrzeug
 */
@Caption("{%kennzeichen}")
@Entity
@Table(name = "fahrzeug", catalog = "gabrieltransport")
public class Fahrzeug implements java.io.Serializable {

	private int idfahrzeug;
	private FahrzeugFunktion fahrzeugFunktion;
	private String kennzeichen;
	private String gewicht;
	private int nummer;

	public Fahrzeug() {
	}

	public Fahrzeug(int idfahrzeug, FahrzeugFunktion fahrzeugFunktion, String kennzeichen, int nummer) {
		this.idfahrzeug = idfahrzeug;
		this.fahrzeugFunktion = fahrzeugFunktion;
		this.kennzeichen = kennzeichen;
		this.nummer = nummer;
	}

	public Fahrzeug(int idfahrzeug, FahrzeugFunktion fahrzeugFunktion, String kennzeichen, String gewicht, int nummer) {
		this.idfahrzeug = idfahrzeug;
		this.fahrzeugFunktion = fahrzeugFunktion;
		this.kennzeichen = kennzeichen;
		this.gewicht = gewicht;
		this.nummer = nummer;
	}

	@Caption("Idfahrzeug")
	@Id

	@Column(name = "idfahrzeug", unique = true, nullable = false, columnDefinition = "INT")
	public int getIdfahrzeug() {
		return this.idfahrzeug;
	}

	public void setIdfahrzeug(int idfahrzeug) {
		this.idfahrzeug = idfahrzeug;
	}

	@Caption("FahrzeugFunktion")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Funktion", nullable = false, columnDefinition = "INT")
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

	@Caption("Gewicht")
	@Column(name = "Gewicht", columnDefinition = "VARCHAR", length = 45)
	public String getGewicht() {
		return this.gewicht;
	}

	public void setGewicht(String gewicht) {
		this.gewicht = gewicht;
	}

	@Caption("Nummer")
	@Column(name = "Nummer", nullable = false, columnDefinition = "INT")
	public int getNummer() {
		return this.nummer;
	}

	public void setNummer(int nummer) {
		this.nummer = nummer;
	}

}
