package de.thm.mni.ssa.bpmn.payment.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record RedeemVoucherInputDto(
        @JsonProperty("customerId") UUID customerId,
        @JsonProperty("voucherCode") String voucherCode
) {
}
