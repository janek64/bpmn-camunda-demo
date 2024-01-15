package de.thm.mni.ssa.bpmn.payment.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "balance_cent")
    private long balanceCent;

    @OneToMany(mappedBy = "redeemCustomer")
    private Set<Voucher> redeemedVoucher = new HashSet<>();

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalanceCent() {
        return balanceCent;
    }

    public void setBalanceCent(long balanceCent) {
        this.balanceCent = balanceCent;
    }

    public double getBalanceEuro() {
        return (double) this.balanceCent / 100;
    }

    public Set<Voucher> getRedeemedVoucher() {
        return redeemedVoucher;
    }
}
