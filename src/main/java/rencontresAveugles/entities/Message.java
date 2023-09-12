package rencontresAveugles.entities;

import java.sql.Timestamp;
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
@Table(name = "message")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	@JsonView(JsonViews.Simple.class)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_emetteur_message", foreignKey = @ForeignKey(name = "id_emetteur_message_fk"))
	@JsonView(JsonViews.Message.class)
	private User emetteur;
	@ManyToOne
	@JoinColumn(name = "id_destinataire_message", foreignKey = @ForeignKey(name = "id_destinataire_message_fk"))
	@JsonView(JsonViews.Message.class)
	private User destinataire;
	@Column(name = "date", nullable = false)
	@JsonView(JsonViews.Simple.class)
	private Timestamp  date;
	@Column(name = "contenu", nullable = false)
	@JsonView(JsonViews.Simple.class)
	private String contenu;
	
	public Message() {
		super();
	}
	public Message(User emetteur, User destinataire, Timestamp date, String contenu) {
		super();
		this.emetteur = emetteur;
		this.destinataire = destinataire;
		this.date = date;
		this.contenu = contenu;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public User getEmetteur() {
		return emetteur;
	}

	public void setEmetteur(User emetteur) {
		this.emetteur = emetteur;
	}

	public User getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(User destinataire) {
		this.destinataire = destinataire;
	}
	
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
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
		Message other = (Message) obj;
		return Objects.equals(id, other.id);
	}
	
}