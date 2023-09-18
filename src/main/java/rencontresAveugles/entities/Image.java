package rencontresAveugles.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import rencontresAveugles.jsonviews.JsonViews;

@Entity
@Table(name="image")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	@JsonView(JsonViews.Simple.class)
	private Long id;
	@Column(name="type")
	@JsonView(JsonViews.Simple.class)
	private String type;
	@Column(name="nom")
	@JsonView(JsonViews.Simple.class)
	private String nom;
	@Lob
	@Column(name="bytes", length = 50000000)
	@JsonView(JsonViews.Image.class)
	private byte[] imageByte;
	@ManyToOne
	@JoinColumn(name="id_user", foreignKey = @ForeignKey(name="id_user_fk"))
	@JsonView(JsonViews.Image.class)
	private User user;
	
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Image() {
		super();
	}


	public Image(String type, String nom, byte[] imageByte, User user) {
		super();
		this.type = type;
		this.nom = nom;
		this.imageByte = imageByte;
		this.user = user;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public byte[] getImageByte() {
		return imageByte;
	}


	public void setImageByte(byte[] imageByte) {
		this.imageByte = imageByte;
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
		Image other = (Image) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
	
	
	
	
}
