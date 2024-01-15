package de.thm.mni.ssa.bpmn.booking.model.exceptions;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(UUID customerId) {
        super(String.format("Could not find customer with ID '%s'", customerId));
    }
}

