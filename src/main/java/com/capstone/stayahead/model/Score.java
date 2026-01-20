package com.capstone.stayahead.model;

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

}
