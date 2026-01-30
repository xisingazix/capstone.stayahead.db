package com.capstone.stayahead.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Table(name = "sponsor")
@AllArgsConstructor
@NoArgsConstructor
@Getter                             // Lombok generated getters (avoid @Data for entities; performance issues)
@Setter
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "Name must not be empty")
    @Size(min = 2 , message = "Name must be 2 characters or more.")
    private String name;

    @Pattern(regexp = "^\\+?\\d{8,15}$", message = "Phone number must be at least 8 digits") //
    private String phone;


    private String address;

    @Column(nullable = false, unique = true)
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",  // regular expression for email  \w-  for alphanumeric & _ email regex: ^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$
            flags = Pattern.Flag.CASE_INSENSITIVE,             // Caps/ non caps will be treated the same
            message = "Email is invalid."
    )
    private String email;

    @Builder
    public Sponsor( String name, String phone, String address, String email) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }


}
