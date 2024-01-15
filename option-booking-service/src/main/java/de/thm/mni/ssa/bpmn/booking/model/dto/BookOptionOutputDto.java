package de.thm.mni.ssa.bpmn.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record BookOptionOutputDto(
        // Using strings is required since quarkus-zeebe does not support ObjectMapper customization
        @JsonProperty("bookingStart") String bookingStart,
        @JsonProperty("bookingEnd") String bookingEnd
) {
}
