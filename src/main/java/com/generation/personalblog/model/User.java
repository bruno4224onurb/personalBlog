package com.generation.personalblog.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "The attribute name is mandatory!")
	private String name;

	@Schema(example = "email@email.com.br")
	@NotNull(message = "The attribute user is mandatory!")
	@Email(message = "The attribute user must be a valid email!")
	private String userName;

	@NotNull(message = "The attribute password is mandatory!")
	@Size(min = 8, message = "The password must have at least 8 characters.")
	private String password;

	@Size(max = 5000, message = "The url of the photo must be less than 5000 characters.")
	private String userPhoto;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("user")
	private List<Posts> posts;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setNome(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String user) {
		this.userName = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public List<Posts> getPost() {
		return posts;
	}

	public void setPost(List<Posts> posts) {
		this.posts = posts;
	}

	public User(Long id, String name, String user, String password, String userPhoto) {
		this.id = id;
		this.name = name;
		this.userName = user;
		this.password = password;
		this.userPhoto = userPhoto;
	}

	public User() {
	}

}
