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
@Table(name = "option")
public class Option {
    @Id
    @GeneratedValue
    @Column(name = "option_id")
    private UUID optionId;

    @Column(name = "name")
    private String name;

    @Column(name = "duration_hours")
    private int durationHours;

    @Column(name = "price_cent")
    private int priceCent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "option")
    private Set<CustomerBooksOption> bookings = new HashSet<>();

    public UUID getOptionId() {
        return optionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public int getPriceCent() {
        return priceCent;
    }

    public void setPriceCent(int priceCent) {
        this.priceCent = priceCent;
    }

    public double getPriceEuro() {
        return (double) this.priceCent / 100;
    }

    public Set<CustomerBooksOption> getBookings() {
        return bookings;
    }
}
