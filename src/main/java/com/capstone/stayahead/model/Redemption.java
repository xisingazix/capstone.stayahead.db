package com.capstone.stayahead.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDate;


@Entity
public class Redemption {


    @EmbeddedId
    private RedemptionId redemptionId;

    @ManyToOne(cascade = CascadeType.ALL)
    // TOcheck : cyclical dependencies from vouchers
    @MapsId("voucherId")
    @JoinColumn(name = "voucher_Id", nullable = false)
    @NotNull(message = "Voucher Id required")
    private Voucher voucher;


    @ManyToOne(cascade = CascadeType.ALL)
    // TOcheck : cyclical dependencies from users
    @MapsId("usersId")
    @JoinColumn(name = "users_Id", nullable = false)
    @NotNull(message = "User Id required")
    private Users users;

    @CreationTimestamp
    @Column(name = "redeem_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyy")
    private LocalDate redeemDate;

    public Redemption() {
    }

    public Redemption(Voucher voucher, Users users) {
        this.voucher = voucher;
        this.users = users;

    }

    public Redemption(RedemptionId redemptionId) {
        this.redemptionId = redemptionId;
    }


    public RedemptionId getRedemptionId() {
        return redemptionId;
    }

    public void setRedemptionId(RedemptionId redemptionId) {
        this.redemptionId = redemptionId;
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
