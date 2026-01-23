package com.capstone.stayahead.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "sponsor_id", nullable = false)
    @NotNull( message = "Please enter a valid id  \"sponsor\" : {\"id\": 1} ")
    private Sponsor sponsor;

    private String name;

    private String description;

    private String terms;

    @CreationTimestamp
    @Column(name = "issued_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyy")
    private LocalDate issueDate;

    @Column(name = "expire_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyy")
    private LocalDate expiryDate;

    public Voucher() {
    }

    public Voucher(Sponsor sponsor, String name, String description, String terms) {
        this.sponsor = sponsor;
        this.name = name;
        this.description = description;
        this.terms = terms;
    }

    public Voucher(Sponsor sponsor, String name, String description, String terms, LocalDate expiryDate) {
        this.sponsor = sponsor;
        this.name = name;
        this.description = description;
        this.terms = terms;
        this.expiryDate = expiryDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
