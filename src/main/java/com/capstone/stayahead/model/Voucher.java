package com.capstone.stayahead.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDate;


@Entity
@Table(name = "voucher")
@AllArgsConstructor
@NoArgsConstructor
@Getter                             // Lombok generated getters (avoid @Data for entities; performance issues)
@Setter
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "sponsor_id", nullable = false)
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


    @Builder
    public Voucher(Sponsor sponsor, String name, String description, String terms, LocalDate expiryDate) {
        this.sponsor = sponsor;
        this.name = name;
        this.description = description;
        this.terms = terms;
        this.expiryDate = expiryDate;
    }

}
