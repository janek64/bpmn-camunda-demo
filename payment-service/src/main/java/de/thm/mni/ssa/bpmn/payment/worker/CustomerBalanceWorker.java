package de.thm.mni.ssa.bpmn.payment.worker;

import de.thm.mni.ssa.bpmn.payment.model.dto.CheckCustomerBalanceInputDto;
import de.thm.mni.ssa.bpmn.payment.model.dto.DeductPriceFromBalanceInputDto;
import de.thm.mni.ssa.bpmn.payment.model.entity.Customer;
import de.thm.mni.ssa.bpmn.payment.model.exception.CustomerNotFoundException;
import de.thm.mni.ssa.bpmn.payment.model.exception.InsufficientBalanceException;
import de.thm.mni.ssa.bpmn.payment.repository.CustomerRepository;
import io.quarkiverse.zeebe.JobWorker;
import io.quarkiverse.zeebe.VariablesAsType;
import io.quarkiverse.zeebe.ZeebeBpmnError;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CustomerBalanceWorker {

    @Inject
    CustomerRepository customerRepository;

    @JobWorker(type = "check-customer-balance")
    public void checkCustomerBalance(@VariablesAsType CheckCustomerBalanceInputDto input) {
        Customer customer = customerRepository.findById(input.customerId());
        if (customer == null) {
            throw new CustomerNotFoundException(input.customerId());
        }
        if (customer.getBalanceCent() < input.optionPriceCent()) {
            throw new ZeebeBpmnError("insufficient-balance", String.format(
                    "Customer balance is %f€, but option price requires %f€",
                    customer.getBalanceEuro(), input.getOptionPriceEuro()
            ));
        }
    }

    @Transactional
    @JobWorker(type = "deduct-price-from-balance")
    public void deductPriceFromBalance(@VariablesAsType DeductPriceFromBalanceInputDto input) {
        Customer customer = customerRepository.findById(input.customerId());
        if (customer == null) {
            throw new CustomerNotFoundException(input.customerId());
        }
        if (customer.getBalanceCent() < input.optionPriceCent()) {
            throw new InsufficientBalanceException(customer.getBalanceEuro(), input.getOptionPriceEuro());
        }
        customer.setBalanceCent(customer.getBalanceCent() - input.optionPriceCent());
        customerRepository.persist(customer);
        Log.infof("Deducted %f€ from balance of customer '%s'", input.getOptionPriceEuro(), customer.getCustomerId());
    }
}
