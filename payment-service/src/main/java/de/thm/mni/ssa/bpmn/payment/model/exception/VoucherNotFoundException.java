package de.thm.mni.ssa.bpmn.payment.model.exception;

public class VoucherNotFoundException extends RuntimeException {
    public VoucherNotFoundException(String voucherCode) {
        super(String.format("Could not find voucher with code '%s'", voucherCode));
    }
}
