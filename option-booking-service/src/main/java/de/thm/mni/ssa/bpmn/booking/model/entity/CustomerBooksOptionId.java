package de.thm.mni.ssa.bpmn.booking.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class CustomerBooksOptionId implements Serializable {
    private UUID customer;
    private UUID option;

    private LocalDateTime startTime;

    public UUID getCustomer() {
        return customer;
    }

    public void setCustomer(UUID customer) {
        this.customer = customer;
    }

    public UUID getOption() {
        return option;
    }

    public void setOption(UUID option) {
        this.option = option;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerBooksOptionId that = (CustomerBooksOptionId) o;
        return Objects.equals(customer, that.customer) &&
                Objects.equals(option, that.option) &&
                Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, option, startTime);
    }
}
