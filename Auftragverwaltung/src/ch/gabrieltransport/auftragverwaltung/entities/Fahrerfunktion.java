package ch.gabrieltransport.auftragverwaltung.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import ch.gabrieltransport.auftragverwaltung.dal.FahrerfunktionDAO;
import com.xdev.dal.DAO;
import com.xdev.util.Caption;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Fahrerfunktion
 */
@DAO(daoClass = FahrerfunktionDAO.class)
@Caption("{%beschreibung}")
@Entity
@Table(name = "fahrerfunktion", catalog = "gabrieltransport")
public class Fahrerfunktion implements java.io.Serializable {

	private int idfunktion;
	private String beschreibung;
	private Set<Fahrerfunktionmap> fahrerfunktionmaps = new HashSet<Fahrerfunktionmap>(0);

	public Fahrerfunktion() {
	}

	public Fahrerfunktion(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Fahrerfunktion(String beschreibung, Set<Fahrerfunktionmap> fahrerfunktionmaps) {
		this.beschreibung = beschreibung;
		this.fahrerfunktionmaps = fahrerfunktionmaps;
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
	@Column(name = "beschreibung", nullable = false, columnDefinition = "VARCHAR", length = 45)
	public String getBeschreibung() {
		return this.beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	@Caption("Fahrerfunktionmaps")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "fahrerfunktion")
	public Set<Fahrerfunktionmap> getFahrerfunktionmaps() {
		return this.fahrerfunktionmaps;
	}

	public void setFahrerfunktionmaps(Set<Fahrerfunktionmap> fahrerfunktionmaps) {
		this.fahrerfunktionmaps = fahrerfunktionmaps;
	}

}
