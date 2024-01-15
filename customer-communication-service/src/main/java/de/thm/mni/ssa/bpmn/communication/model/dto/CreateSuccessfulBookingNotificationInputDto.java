package de.thm.mni.ssa.bpmn.communication.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateSuccessfulBookingNotificationInputDto(
        @JsonProperty("customerId") UUID customerId,
        @JsonProperty("optionName") String optionName,
        // Using strings is required since quarkus-zeebe does not support ObjectMapper customization
        @JsonProperty("bookingStart") String bookingStart,
        @JsonProperty("bookingEnd") String bookingEnd
) {
}
