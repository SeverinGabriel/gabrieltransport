package ch.gabrieltransport.auftragverwaltung.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.xdev.dal.DAO;
import com.xdev.util.Caption;

import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugDAO;

/**
 * Fahrzeug
 */
@DAO(daoClass = FahrzeugDAO.class)
@Caption("{%kennzeichen}")
@Entity
@Table(name = "fahrzeug", catalog = "gabrieltransport")
public class Fahrzeug implements java.io.Serializable {

	private int idfahrzeug;
	private String kennzeichen;
	private String gewicht;
	private Set<Fahrzeugauftrag> fahrzeugauftrags = new HashSet<Fahrzeugauftrag>(0);

	public Fahrzeug() {
	}

	public Fahrzeug(int idfahrzeug, String kennzeichen) {
		this.idfahrzeug = idfahrzeug;
		this.kennzeichen = kennzeichen;
	}

	public Fahrzeug(int idfahrzeug, String kennzeichen, String gewicht, Set<Fahrzeugauftrag> fahrzeugauftrags) {
		this.idfahrzeug = idfahrzeug;
		this.kennzeichen = kennzeichen;
		this.gewicht = gewicht;
		this.fahrzeugauftrags = fahrzeugauftrags;
	}

	@Caption("Idfahrzeug")
	@Id

	@Column(name = "idfahrzeug", unique = true, nullable = false)
	public int getIdfahrzeug() {
		return this.idfahrzeug;
	}

	public void setIdfahrzeug(int idfahrzeug) {
		this.idfahrzeug = idfahrzeug;
	}

	@Caption("Kennzeichen")
	@Column(name = "Kennzeichen", nullable = false, length = 45)
	public String getKennzeichen() {
		return this.kennzeichen;
	}

	public void setKennzeichen(String kennzeichen) {
		this.kennzeichen = kennzeichen;
	}

	@Caption("Gewicht")
	@Column(name = "Gewicht", length = 45)
	public String getGewicht() {
		return this.gewicht;
	}

	public void setGewicht(String gewicht) {
		this.gewicht = gewicht;
	}

	@Caption("Fahrzeugauftrags")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fahrzeug")
	public Set<Fahrzeugauftrag> getFahrzeugauftrags() {
		return this.fahrzeugauftrags;
	}

	public void setFahrzeugauftrags(Set<Fahrzeugauftrag> fahrzeugauftrags) {
		this.fahrzeugauftrags = fahrzeugauftrags;
	}

}
