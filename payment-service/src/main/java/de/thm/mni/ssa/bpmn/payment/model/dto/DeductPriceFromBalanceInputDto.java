package de.thm.mni.ssa.bpmn.payment.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record DeductPriceFromBalanceInputDto(
        @JsonProperty("customerId") UUID customerId,
        @JsonProperty("optionPriceCent") int optionPriceCent
) {
    public double getOptionPriceEuro() {
        return (double) this.optionPriceCent / 100;
    }
}
