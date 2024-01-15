package de.thm.mni.ssa.bpmn.payment.model.exception;

import java.util.UUID;

public class VoucherAlreadyRedeemedException extends RuntimeException {
    public VoucherAlreadyRedeemedException(String voucherCode, UUID redeemCustomer) {
        super(String.format(
                "Voucher with code '%s' was already redeemed by customer '%s'",
                voucherCode, redeemCustomer
        ));
    }
}
