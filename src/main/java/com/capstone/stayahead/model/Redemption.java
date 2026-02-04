package com.capstone.stayahead.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDate;


@Entity
@Table(name = "redemption")
@AllArgsConstructor
@NoArgsConstructor
@Getter                             // Lombok generated getters (avoid @Data for entities; performance issues)
@Setter
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
    // TOcheck : cyclical dependencies from users,
    @MapsId("usersId")
    @JoinColumn(name = "users_Id", nullable = false)
    @NotNull(message = "User Id required")
    private Users users;

    @CreationTimestamp
    @Column(name = "redeem_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyy")
    private LocalDate redeemDate;


    @Builder
    public Redemption(RedemptionId redemptionId, Voucher voucher, Users users) {
        this.redemptionId = redemptionId;
        this.voucher = voucher;
        this.users = users;
    }


}
