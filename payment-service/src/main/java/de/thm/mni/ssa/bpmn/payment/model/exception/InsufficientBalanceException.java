package de.thm.mni.ssa.bpmn.payment.model.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(double balanceEuro, double optionPriceEuro) {
        super(String.format(
                "Customer balance of %f€ is insufficient to book option with price %f€",
                balanceEuro, optionPriceEuro
        ));
    }
}
