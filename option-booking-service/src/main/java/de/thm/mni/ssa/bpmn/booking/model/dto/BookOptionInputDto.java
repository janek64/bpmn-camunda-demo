package de.thm.mni.ssa.bpmn.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record BookOptionInputDto(
        @JsonProperty("customerId") UUID customerId,
        @JsonProperty("optionId") UUID optionId
) {
}
