package de.thm.mni.ssa.bpmn.booking.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<CustomerBooksOption> optionBookings = new HashSet<>();

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CustomerBooksOption> getOptionBookings() {
        return optionBookings;
    }
}
