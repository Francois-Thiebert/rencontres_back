
package rencontresAveugles.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@ManyToOne
	@JoinColumn(name="user", foreignKey = @ForeignKey(name="user_fk"))
	@JsonView(JsonViews.Reponse.class)
	private User user;
	@ManyToOne
	@JoinColumn(name="question", foreignKey = @ForeignKey(name="question_fk"))
	@JsonView(JsonViews.Reponse.class)
	private Question question;
	@Column(name = "numeroReponse")
	@JsonView(JsonViews.Simple.class)
	private int numeroReponse;

	public Reponse() {
		super();
	}

	public Reponse(User user, Question question, int numeroReponse) {
		super();
		this.user = user;
		this.question = question;
		this.numeroReponse = numeroReponse;
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

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getNumeroReponse() {
		return numeroReponse;
	}

	public void setNumeroReponse(int numeroReponse) {
		this.numeroReponse = numeroReponse;
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