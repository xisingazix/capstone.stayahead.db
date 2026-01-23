package com.capstone.stayahead.model;

import jakarta.persistence.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Entity
public class Redemption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    // TODO : cyclical dependencies from vouchers
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;


    @ManyToOne
    // TODO : cyclical dependencies from users
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    @Column(name = "redeem_date")
    private LocalDateTime redeemDate;

    public Redemption() {
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
