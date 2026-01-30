    package com.capstone.stayahead.model;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import jakarta.persistence.*;
    import lombok.*;
    import org.hibernate.annotations.OnDelete;
    import org.hibernate.annotations.OnDeleteAction;
    import org.hibernate.annotations.UpdateTimestamp;

    import java.time.LocalDateTime;

    @Entity
    @Table(name = "score")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter                             // Lombok generated getters (avoid @Data for entities; performance issues)
    @Setter
    public class Score {

        @Id
        private Integer id;

        @OneToOne(fetch = FetchType.LAZY, optional = false)
        @MapsId                                 // Ensure Id same as user id
        @JoinColumn(name = "user_id")           // Foreign Key user_id
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JsonBackReference
        private  Users users;

        @Column(nullable = false, updatable = true)
        private Integer score = 0;

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;

        @Builder
        public Score(Users users, Integer score) {
            this.users = users;
            this.score = score;
        }


      }
