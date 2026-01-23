package com.capstone.stayahead.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.scheduling.support.SimpleTriggerContext;

@Entity
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "Name must not be empty.")
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

    public Sponsor() {
    }

    public Sponsor(Integer id, String name, String phone, String address, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
