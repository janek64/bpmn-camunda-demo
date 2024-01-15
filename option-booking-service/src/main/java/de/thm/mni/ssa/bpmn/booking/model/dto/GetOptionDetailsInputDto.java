package de.thm.mni.ssa.bpmn.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record GetOptionDetailsInputDto(
        @JsonProperty("optionId") UUID optionId
) {
}
