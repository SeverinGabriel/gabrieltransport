package ch.gabrieltransport.auftragverwaltung.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import com.xdev.util.Caption;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * FahrzeugFunktion
 */
@Caption("{%beschreibung}")
@Entity
@Table(name = "fahrzeug_funktion", catalog = "gabrieltransport")
public class FahrzeugFunktion implements java.io.Serializable {

	private int idfunktion;
	private String beschreibung;
	private Set<Fahrzeug> fahrzeugs = new HashSet<Fahrzeug>(0);

	public FahrzeugFunktion() {
	}

	public FahrzeugFunktion(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public FahrzeugFunktion(String beschreibung, Set<Fahrzeug> fahrzeugs) {
		this.beschreibung = beschreibung;
		this.fahrzeugs = fahrzeugs;
	}

	@Caption("Idfunktion")
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idfunktion", unique = true, nullable = false, columnDefinition = "INT")
	public int getIdfunktion() {
		return this.idfunktion;
	}

	public void setIdfunktion(int idfunktion) {
		this.idfunktion = idfunktion;
	}

	@Caption("Beschreibung")
	@Column(name = "Beschreibung", nullable = false, columnDefinition = "VARCHAR", length = 45)
	public String getBeschreibung() {
		return this.beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	@Caption("Fahrzeugs")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fahrzeugFunktion")
	public Set<Fahrzeug> getFahrzeugs() {
		return this.fahrzeugs;
	}

	public void setFahrzeugs(Set<Fahrzeug> fahrzeugs) {
		this.fahrzeugs = fahrzeugs;
	}

}
