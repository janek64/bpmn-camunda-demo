package de.thm.mni.ssa.bpmn.booking.model.exceptions;

import java.util.UUID;

public class OptionNotFoundException extends RuntimeException{
    public OptionNotFoundException(UUID optionId) {
        super(String.format("Could not find option with ID '%s'", optionId));
    }
}
