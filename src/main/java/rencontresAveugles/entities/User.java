package rencontresAveugles.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonView;

import rencontresAveugles.jsonviews.JsonViews;

@Entity
@Table(name = "user")
public class User implements UserDetails{
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name="user_id")
	@JsonView(JsonViews.Simple.class)
	private Long id;
	@OneToMany(mappedBy = "user1")
	@JsonView(JsonViews.UsertWithAll.class)
	private Set<Match> matchs1;
	@OneToMany(mappedBy = "user2")
	@JsonView(JsonViews.UsertWithAll.class)
	private Set<Match> matchs2;
	@OneToMany(mappedBy = "emetteur")
	@JsonView(JsonViews.UsertWithAll.class)
	private Set<Message> messagesEmis;
	@OneToMany(mappedBy = "destinataire")
	@JsonView(JsonViews.UsertWithAll.class)
	private Set<Message> messagesReçus;
	@Column(name = "prenom", nullable = false)
	@JsonView(JsonViews.Simple.class)
	private String prenom;
	@Column(name = "age", nullable = false)
	@JsonView(JsonViews.Simple.class)
	private int age;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "reponse_profil", foreignKey = @ForeignKey(name = "reponse_profil_fk"))
	@JsonView(JsonViews.UsertWithAll.class)
	private Reponse reponse;
	@Column(name = "photo1", nullable = true)
	@JsonView({JsonViews.UsertWithAll.class, JsonViews.Match.class,JsonViews.Message.class})
	private String photo1;
	@Column(name = "photo2", nullable = true)
	@JsonView(JsonViews.UsertWithAll.class)
	private String photo2;
	@Column(name = "photo3", nullable = true)
	@JsonView(JsonViews.UsertWithAll.class)
	private String photo3;
	@Column(name = "photos4", nullable = true)
	@JsonView(JsonViews.UsertWithAll.class)
	private String[] photos4;
	@Column(name = "photos5", nullable = true)
	@JsonView(JsonViews.UsertWithAll.class)
	private String[] photos5;
	@Column (name = "login", nullable = false, unique = true)
	@JsonView(JsonViews.Simple.class)
	private String login;
	@Column(name = "password", length = 255, nullable = false)
	private String password;
	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	@JsonView(JsonViews.Simple.class)
	private Role role;
	@OneToMany(mappedBy = "user")
	@JsonView(JsonViews.UsertWithAll.class)
	private Set<Image> photos;
	
	public User() {
		super();
	}
	
	public User(String prenom, int age, String login, String password, Role role) {
		super();
		this.prenom = prenom;
		this.age = age;
		this.login = login;
		this.password = password;
		this.role = role;
	}

	public Set<Image> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<Image> photos) {
		this.photos = photos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Match> getMatchs1() {
		return matchs1;
	}

	public void setMatchs1(Set<Match> matchs1) {
		this.matchs1 = matchs1;
	}

	public Set<Match> getMatchs2() {
		return matchs2;
	}

	public void setMatchs2(Set<Match> matchs2) {
		this.matchs2 = matchs2;
	}

	public Set<Message> getMessagesEmis() {
		return messagesEmis;
	}

	public void setMessagesEmis(Set<Message> messagesEmis) {
		this.messagesEmis = messagesEmis;
	}

	public Set<Message> getMessagesReçus() {
		return messagesReçus;
	}

	public void setMessagesReçus(Set<Message> messagesReçus) {
		this.messagesReçus = messagesReçus;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Reponse getReponse() {
		return reponse;
	}

	public void setReponse(Reponse reponse) {
		this.reponse = reponse;
	}

	public String getPhoto1() {
		return photo1;
	}

	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}

	public String getPhoto2() {
		return photo2;
	}

	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}

	public String getPhoto3() {
		return photo3;
	}

	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}

	public String[] getPhotos4() {
		return photos4;
	}

	public void setPhotos4(String[] photos4) {
		this.photos4 = photos4;
	}

	public String[] getPhotos5() {
		return photos5;
	}

	public void setPhotos5(String[] photos5) {
		this.photos5 = photos5;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//new SimpleGrantedAuthority("ROLE_"+this.getClass().getSimpleName());
		return Arrays.asList(new SimpleGrantedAuthority(role.toString()));
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return login;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
}
