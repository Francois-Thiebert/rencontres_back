package rencontresAveugles.entities;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import rencontresAveugles.jsonviews.JsonViews;


@Entity
@Table(name = "match_table")
public class Match {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "match_id")
	@JsonView(JsonViews.Simple.class)
	private Long id;
	@ManyToOne
	@JoinColumn(name="id_user1_match", foreignKey = @ForeignKey(name="id_user1_match_fk"))
	@JsonView(JsonViews.Match.class)
	private User user1;
	@ManyToOne
	@JoinColumn(name="id_user2_match", foreignKey = @ForeignKey(name="id_user2_match_fk"))
	@JsonView(JsonViews.Match.class)
	private User user2;
	@Column(name = "date", nullable = false)
	@JsonView(JsonViews.Simple.class)
	private LocalDate date;
	@Column(name = "compteur", nullable = false)
	@JsonView(JsonViews.Simple.class)
	private int compteur;
	@Column(name = "compatibilite", nullable = false)
	@JsonView(JsonViews.Simple.class)
	private int compatibilite;
	
	public Match() {
		super();
	}
	public Match(User user1, User user2, LocalDate date, int compteur, int compatibilite) {
		super();
		this.user1 = user1;
		this.user2 = user2;
		this.date = date;
		this.compteur = compteur;
		this.compatibilite = compatibilite;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public User getUser1() {
		return user1;
	}
	public void setUser1(User user1) {
		this.user1 = user1;
	}
	public User getUser2() {
		return user2;
	}
	public void setUser2(User user2) {
		this.user2 = user2;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getCompteur() {
		return compteur;
	}
	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}
	public int getCompatibilite() {
		return compatibilite;
	}
	public void setCompatibilite(int compatibilite) {
		this.compatibilite = compatibilite;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		return Objects.equals(id, other.id);
	}

}
