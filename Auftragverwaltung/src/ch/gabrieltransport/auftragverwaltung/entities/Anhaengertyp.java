package ch.gabrieltransport.auftragverwaltung.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import ch.gabrieltransport.auftragverwaltung.dal.AnhaengertypDAO;
import com.xdev.dal.DAO;
import com.xdev.util.Caption;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Anhaengertyp
 */
@DAO(daoClass = AnhaengertypDAO.class)
@Caption("{%beschreibung}")
@Entity
@Table(name = "anhaengertyp", catalog = "gabrieltransport")
public class Anhaengertyp implements java.io.Serializable {

	private int idanhaengertyp;
	private String beschreibung;
	private Set<Fahrzeug> fahrzeugs = new HashSet<Fahrzeug>(0);
	private Set<Anhaenger> anhaengers = new HashSet<Anhaenger>(0);

	public Anhaengertyp() {
	}

	public Anhaengertyp(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Anhaengertyp(String beschreibung, Set<Fahrzeug> fahrzeugs, Set<Anhaenger> anhaengers) {
		this.beschreibung = beschreibung;
		this.fahrzeugs = fahrzeugs;
		this.anhaengers = anhaengers;
	}

	@Caption("Idanhaengertyp")
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idanhaengertyp", unique = true, nullable = false, columnDefinition = "INT")
	public int getIdanhaengertyp() {
		return this.idanhaengertyp;
	}

	public void setIdanhaengertyp(int idanhaengertyp) {
		this.idanhaengertyp = idanhaengertyp;
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "anhaengertyp")
	public Set<Fahrzeug> getFahrzeugs() {
		return this.fahrzeugs;
	}

	public void setFahrzeugs(Set<Fahrzeug> fahrzeugs) {
		this.fahrzeugs = fahrzeugs;
	}

	@Caption("Anhaengers")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "anhaengertyp")
	public Set<Anhaenger> getAnhaengers() {
		return this.anhaengers;
	}

	public void setAnhaengers(Set<Anhaenger> anhaengers) {
		this.anhaengers = anhaengers;
	}

}
