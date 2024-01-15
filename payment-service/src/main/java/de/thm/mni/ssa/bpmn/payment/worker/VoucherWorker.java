package de.thm.mni.ssa.bpmn.payment.worker;

import de.thm.mni.ssa.bpmn.payment.model.dto.CheckVoucherValidityInputDto;
import de.thm.mni.ssa.bpmn.payment.model.dto.RedeemVoucherInputDto;
import de.thm.mni.ssa.bpmn.payment.model.entity.Customer;
import de.thm.mni.ssa.bpmn.payment.model.entity.Voucher;
import de.thm.mni.ssa.bpmn.payment.model.exception.CustomerNotFoundException;
import de.thm.mni.ssa.bpmn.payment.model.exception.VoucherAlreadyRedeemedException;
import de.thm.mni.ssa.bpmn.payment.model.exception.VoucherNotFoundException;
import de.thm.mni.ssa.bpmn.payment.repository.CustomerRepository;
import de.thm.mni.ssa.bpmn.payment.repository.VoucherRepository;
import io.quarkiverse.zeebe.JobWorker;
import io.quarkiverse.zeebe.VariablesAsType;
import io.quarkiverse.zeebe.ZeebeBpmnError;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ApplicationScoped
public class VoucherWorker {

    @Inject
    VoucherRepository voucherRepository;
    @Inject
    CustomerRepository customerRepository;

    @JobWorker(type = "check-voucher-validity")
    public void checkVoucherValidity(@VariablesAsType CheckVoucherValidityInputDto input) {
        Voucher voucher = voucherRepository.findByCodeWithCustomer(input.voucherCode());
        if (voucher == null) {
            throw new ZeebeBpmnError("invalid-voucher", String.format(
                    "No voucher exists for code '%s'", input.voucherCode()
            ));
        } else if (voucher.getRedeemCustomer() != null) {
            throw new ZeebeBpmnError("invalid-voucher", String.format(
                    "Voucher '%s' was already redeemed by customer '%s'",
                    input.voucherCode(), voucher.getRedeemCustomer().getCustomerId()
            ));
        }
    }

    @Transactional
    @JobWorker(type = "redeem-voucher")
    public void redeemVoucher(@VariablesAsType RedeemVoucherInputDto input) {
        Customer customer = customerRepository.findById(input.customerId());
        if (customer == null) {
            throw new CustomerNotFoundException(input.customerId());
        }
        Voucher voucher = voucherRepository.findByCodeWithCustomer(input.voucherCode());
        if (voucher == null) {
            throw new VoucherNotFoundException(input.voucherCode());
        } else if (voucher.getRedeemCustomer() != null) {
            throw new VoucherAlreadyRedeemedException(input.voucherCode(), voucher.getRedeemCustomer().getCustomerId());
        }
        voucher.setRedeemCustomer(customer);
        voucher.setRedeemTime(LocalDateTime.now(ZoneOffset.UTC));
        Panache.getEntityManager().merge(voucher);
        Log.infof("Redeemed voucher with code '%s' for customer '%s'", voucher.getCode(), customer.getCustomerId());
    }
}
