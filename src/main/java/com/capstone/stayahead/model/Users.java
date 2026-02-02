package com.capstone.stayahead.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter                             // Lombok generated getters (avoid @Data for entities; performance issues)
@Setter
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false, unique = true)
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",  // regular expression for email  \w-  for alphanumeric & _ email regex: ^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$
            flags = Pattern.Flag.CASE_INSENSITIVE,             // Caps/ non caps will be treated the same
            message = "Email is invalid."
    )
    private String email;

    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role cannot be blank.")
    private EnumRole role = EnumRole.USER;

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


    // Transferred frm user authentication
    @ToString.Exclude                                      // Precision (field level): Prevents passwords from being printed
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Hide password in responses
    @Column(nullable = false)
    @NotBlank(message = "Password cannot be blank.")
    private String password;

    @Column
    private String userProfileImage;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {   // UserDetails.getUsername treats the email as the username
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    /**
     *********************************************************************
     @Builder eradicates overloading constructors
     - Implement a Builder Pattern to chain syntax at instantiation
     - The constructor is created with only the fields for the builder
     - Note: Place @Builder here instead of at the top of the class
      *********************************************************************
     Example of using the builder pattern:
     Users users = Users.builder()
     .userName("John")
     .email("john@email.com")
     .role(EnumRole.ADMIN)
     .build();
     *********************************************************************
     */
    @Builder
    public Users(
                String email,
                String password,
                EnumRole role,
                String firstName,
                String lastName,
                Score score,
                String userProfileImage)
    {
        this.email = email;
        this.password = password;
        this.role = (role == null) ? EnumRole.USER : role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = score;
        this.userProfileImage = userProfileImage;
    }


}
