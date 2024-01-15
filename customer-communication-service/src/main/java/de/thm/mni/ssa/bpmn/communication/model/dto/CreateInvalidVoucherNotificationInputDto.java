package de.thm.mni.ssa.bpmn.communication.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CreateInvalidVoucherNotificationInputDto(
        @JsonProperty("voucherCode") String voucherCode,
        @JsonProperty("customerId") UUID customerId,
        @JsonProperty("optionName") String optionName

) {
}
