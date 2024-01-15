package de.thm.mni.ssa.bpmn.payment.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckVoucherValidityInputDto(
        @JsonProperty("voucherCode") String voucherCode
) {
}
