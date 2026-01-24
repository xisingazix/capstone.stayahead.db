package com.capstone.stayahead.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "First name must not be empty.")
    @Size(min = 2 , message = "First name must be 2 characters or more.")
    private String firstName;

    @Column(nullable = false)
    @NotNull(message = "Last name must not be empty.")
    @Size(min = 2 , message = "Last name must be 2 characters or more.")
    private String lastName;

    @Column(nullable = false, unique = true)
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",  // regular expression for email  \w-  for alphanumeric & _ email regex: ^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$
            flags = Pattern.Flag.CASE_INSENSITIVE,             // Caps/ non caps will be treated the same
            message = "Email is invalid."
    )
    private String email;

    @Column(nullable = true)
    private String role;

    // Automatically created when entity is created and updated
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

     // prevent infinite JSON response loop
    @OneToOne(
            mappedBy = "users",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private  Score score;

    public Users(){

    }

    public Users(String firstName, String lastName, String email, String role ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;

    }


    @PrePersist
    public void createScoreIfNil(){
        if(this.score == null){
            this.score = new Score(this, 0);
        }
    }

    public Score getScore() {
        return score;
    }


    public void setScore(Score score) {
        this.score = score;
        score.setUsers(this);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
