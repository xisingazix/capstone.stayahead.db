package com.capstone.stayahead.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Redemption {

    @EmbeddedId
    private RedemptionId redemptionId;


    @ManyToOne(cascade = CascadeType.ALL)
    // TODO : cyclical dependencies from vouchers
    @MapsId("voucherId")
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;


    @OneToOne(cascade = CascadeType.ALL)
    // TODO : cyclical dependencies from users
    @MapsId("usersId")
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    @Column(name = "redeem_date")
    private LocalDateTime redeemDate;

    public Redemption() {
    }

    public Redemption(RedemptionId redemptionId, Voucher voucher, Users users, LocalDateTime redeemDate) {
        this.redemptionId = redemptionId;
        this.voucher = voucher;
        this.users = users;
        this.redeemDate = redeemDate;
    }
}
