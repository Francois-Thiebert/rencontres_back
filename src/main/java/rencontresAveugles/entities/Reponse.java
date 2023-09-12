
package rencontresAveugles.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import rencontresAveugles.jsonviews.JsonViews;

@Entity
@Table(name = "reponse")
public class Reponse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reponse_id")
	@JsonView(JsonViews.Simple.class)
	private Long id;
	@OneToOne(mappedBy = "reponse")
	@JsonView(JsonViews.Reponse.class)
	private User user;
	@Column(name = "reponse1")
	@JsonView(JsonViews.Simple.class)
	private int reponse1;
	@Column(name = "reponse2")
	@JsonView(JsonViews.Simple.class)
	private int reponse2;
	@Column(name = "reponse3")
	@JsonView(JsonViews.Simple.class)
	private int reponse3;
	@Column(name = "reponse4")
	@JsonView(JsonViews.Simple.class)
	private int reponse4;
	@Column(name = "reponse5")
	@JsonView(JsonViews.Simple.class)
	private int reponse5;
	@Column(name = "reponse6")
	@JsonView(JsonViews.Simple.class)
	private int reponse6;
	
	public Reponse() {
		super();
	}
	
	public Reponse(User user, int reponse1, int reponse2, int reponse3, int reponse4, int reponse5, int reponse6) {
		super();
		this.user = user;
		this.reponse1 = reponse1;
		this.reponse2 = reponse2;
		this.reponse3 = reponse3;
		this.reponse4 = reponse4;
		this.reponse5 = reponse5;
		this.reponse6 = reponse6;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getReponse1() {
		return reponse1;
	}
	public void setReponse1(int reponse1) {
		this.reponse1 = reponse1;
	}
	public int getReponse2() {
		return reponse2;
	}
	public void setReponse2(int reponse2) {
		this.reponse2 = reponse2;
	}
	public int getReponse3() {
		return reponse3;
	}
	public void setReponse3(int reponse3) {
		this.reponse3 = reponse3;
	}
	public int getReponse4() {
		return reponse4;
	}
	public void setReponse4(int reponse4) {
		this.reponse4 = reponse4;
	}
	public int getReponse5() {
		return reponse5;
	}
	public void setReponse5(int reponse5) {
		this.reponse5 = reponse5;
	}
	public int getReponse6() {
		return reponse6;
	}
	public void setReponse6(int reponse6) {
		this.reponse6 = reponse6;
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
		Reponse other = (Reponse) obj;
		return Objects.equals(id, other.id);
	}
	
}