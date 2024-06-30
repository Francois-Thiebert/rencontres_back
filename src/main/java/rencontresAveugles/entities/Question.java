package rencontresAveugles.entities;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import rencontresAveugles.jsonviews.JsonViews;

@Entity
@Table(name = "question")
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	@JsonView(JsonViews.Simple.class)
	private Long id;
	@Column(name = "intitule")
	@JsonView(JsonViews.Simple.class)
	private String intitule;
	@Column(name = "correspondance")
	@JsonView(JsonViews.Simple.class)
	private Long correspondance;
	@Column(name ="priorite")
	@JsonView(JsonViews.Simple.class)
	private Long priorite;
	@Column(name = "reponse1")
	@JsonView(JsonViews.Simple.class)
	private String reponse1;
	@Column(name = "reponse2")
	@JsonView(JsonViews.Simple.class)
	private String reponse2;
	@Column(name = "reponse3")
	@JsonView(JsonViews.Simple.class)
	private String reponse3;
	@Column(name = "reponse4")
	@JsonView(JsonViews.Simple.class)
	private String reponse4;
	@Column(name = "reponse5")
	@JsonView(JsonViews.Simple.class)
	private String reponse5;
	@Column(name = "reponse6")
	@JsonView(JsonViews.Simple.class)
	private String reponse6;
	@Column(name = "reponse7")
	@JsonView(JsonViews.Simple.class)
	private String reponse7;
	@Column(name = "reponse8")
	@JsonView(JsonViews.Simple.class)
	private String reponse8;
	@OneToMany(mappedBy = "question")
	@JsonView(JsonViews.Question.class)
	private Set<Reponse> reponses;
	
	public Question() {
		super();
	}

	public Question(String intitule, Long correspondance, Long priorite, String reponse1, String reponse2,
			String reponse3, String reponse4, String reponse5, String reponse6, String reponse7, String reponse8,
			Set<Reponse> reponses) {
		super();
		this.intitule = intitule;
		this.correspondance = correspondance;
		this.priorite = priorite;
		this.reponse1 = reponse1;
		this.reponse2 = reponse2;
		this.reponse3 = reponse3;
		this.reponse4 = reponse4;
		this.reponse5 = reponse5;
		this.reponse6 = reponse6;
		this.reponse7 = reponse7;
		this.reponse8 = reponse8;
		this.reponses = reponses;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	

	public Long getPriorite() {
		return priorite;
	}

	public void setPriorite(Long priorite) {
		this.priorite = priorite;
	}

	public String getReponse1() {
		return reponse1;
	}

	public void setReponse1(String reponse1) {
		this.reponse1 = reponse1;
	}

	public String getReponse2() {
		return reponse2;
	}

	public void setReponse2(String reponse2) {
		this.reponse2 = reponse2;
	}

	public String getReponse3() {
		return reponse3;
	}

	public void setReponse3(String reponse3) {
		this.reponse3 = reponse3;
	}

	public String getReponse4() {
		return reponse4;
	}

	public void setReponse4(String reponse4) {
		this.reponse4 = reponse4;
	}

	public String getReponse5() {
		return reponse5;
	}

	public void setReponse5(String reponse5) {
		this.reponse5 = reponse5;
	}

	public String getReponse6() {
		return reponse6;
	}

	public void setReponse6(String reponse6) {
		this.reponse6 = reponse6;
	}

	public String getReponse7() {
		return reponse7;
	}

	public void setReponse7(String reponse7) {
		this.reponse7 = reponse7;
	}

	public String getReponse8() {
		return reponse8;
	}

	public void setReponse8(String reponse8) {
		this.reponse8 = reponse8;
	}

	public Long getCorrespondance() {
		return correspondance;
	}

	public void setCorrespondance(Long correspondance) {
		this.correspondance = correspondance;
	}

	public Set<Reponse> getReponses() {
		return reponses;
	}

	public void setReponses(Set<Reponse> reponses) {
		this.reponses = reponses;
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
		Question other = (Question) obj;
		return Objects.equals(id, other.id);
	}
	
	

}
