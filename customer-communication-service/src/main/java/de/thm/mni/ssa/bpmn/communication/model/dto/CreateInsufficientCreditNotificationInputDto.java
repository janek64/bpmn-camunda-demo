package de.thm.mni.ssa.bpmn.communication.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CreateInsufficientCreditNotificationInputDto(
        @JsonProperty("customerId") UUID customerId,
        @JsonProperty("optionName") String optionName,
        @JsonProperty("priceCent") int priceCent
) {
    public double getPriceEuro() {
        return (double) this.priceCent / 100;
    }
}
