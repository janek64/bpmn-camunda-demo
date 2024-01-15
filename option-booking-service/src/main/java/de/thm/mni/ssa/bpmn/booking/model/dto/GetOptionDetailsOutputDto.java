package de.thm.mni.ssa.bpmn.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetOptionDetailsOutputDto(
        @JsonProperty("name") String name,
        @JsonProperty("durationHours") int durationHours,
        @JsonProperty("priceCent") int priceCent
) {
}
