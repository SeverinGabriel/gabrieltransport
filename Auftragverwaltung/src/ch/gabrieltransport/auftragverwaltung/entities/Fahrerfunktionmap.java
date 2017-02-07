package ch.gabrieltransport.auftragverwaltung.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import ch.gabrieltransport.auftragverwaltung.dal.FahrerfunktionmapDAO;
import com.xdev.dal.DAO;
import com.xdev.util.Caption;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Fahrerfunktionmap
 */
@DAO(daoClass = FahrerfunktionmapDAO.class)
@Caption("{%idfahrerfunktion}")
@Entity
@Table(name = "fahrerfunktionmap", catalog = "gabrieltransport")
public class Fahrerfunktionmap implements java.io.Serializable {

	private int idfahrerfunktion;
	private Fahrer fahrer;
	private Fahrerfunktion fahrerfunktion;

	public Fahrerfunktionmap() {
	}

	public Fahrerfunktionmap(Fahrer fahrer, Fahrerfunktion fahrerfunktion) {
		this.fahrer = fahrer;
		this.fahrerfunktion = fahrerfunktion;
	}

	@Caption("Idfahrerfunktion")
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idfahrerfunktion", unique = true, nullable = false, columnDefinition = "INT")
	public int getIdfahrerfunktion() {
		return this.idfahrerfunktion;
	}

	public void setIdfahrerfunktion(int idfahrerfunktion) {
		this.idfahrerfunktion = idfahrerfunktion;
	}

	@Caption("Fahrer")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fkFahrer", nullable = false, columnDefinition = "INT")
	public Fahrer getFahrer() {
		return this.fahrer;
	}

	public void setFahrer(Fahrer fahrer) {
		this.fahrer = fahrer;
	}

	@Caption("Fahrerfunktion")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fkFunktion", nullable = false, columnDefinition = "INT")
	public Fahrerfunktion getFahrerfunktion() {
		return this.fahrerfunktion;
	}

	public void setFahrerfunktion(Fahrerfunktion fahrerfunktion) {
		this.fahrerfunktion = fahrerfunktion;
	}

}
