package com.bruno2442onurb.personalBlog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "tb_users")
@Schema(description = "User entity representing a user of the blog")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the User", example = "1", required = true)
    private Long id;

    @NotNull(message = "Name is required")
    @Schema(description = "Name of the User", example = "John Doe", required = true)
    private String name;

    @Schema(description = "URL to the profile photo of the User", example = "http://example.com/photo.jpg")
    private String photo;

    @NotNull(message = "Email is required")
    @Email(message = "Email must be valid")
    @Schema(description = "Email of the User", example = "john.doe@example.com", required = true)
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password must have at least 6 characters")
    @Schema(description = "Password of the User", example = "secret", required = true)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("user")
    @Schema(description = "Posts written by the User")
    private List<Post> posts;
    
    public User(List<Post> posts) {
        this.posts = posts;
    }

    public User(Long id, String name, String email, String password, String photo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.photo = photo;
    }
    
    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
