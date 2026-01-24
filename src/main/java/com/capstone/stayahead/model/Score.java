    package com.capstone.stayahead.model;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import jakarta.persistence.*;
    import org.hibernate.annotations.OnDelete;
    import org.hibernate.annotations.OnDeleteAction;
    import org.hibernate.annotations.UpdateTimestamp;

    import java.time.LocalDateTime;

    @Entity
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

        public Score(Users users, Integer score) {
            this.users = users;
            this.score = score;
        }

        public Score() {
        }

        public Users getUsers() {
            return users;
        }

        public void setUsers(Users users) {
            this.users = users;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
