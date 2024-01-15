package de.thm.mni.ssa.bpmn.payment.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue
    @Column(name = "voucher_id")
    private UUID voucherId;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "redeem_customer_id")
    private Customer redeemCustomer;

    @Column(name = "redeem_time")
    private LocalDateTime redeemTime;

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Customer getRedeemCustomer() {
        return redeemCustomer;
    }

    public void setRedeemCustomer(Customer redeemCustomer) {
        this.redeemCustomer = redeemCustomer;
    }

    public LocalDateTime getRedeemTime() {
        return redeemTime;
    }

    public void setRedeemTime(LocalDateTime redeemTime) {
        this.redeemTime = redeemTime;
    }
}
