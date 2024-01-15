package de.thm.mni.ssa.bpmn.payment.resource;

import de.thm.mni.ssa.bpmn.payment.model.entity.Voucher;
import de.thm.mni.ssa.bpmn.payment.repository.VoucherRepository;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("/vouchers")
public class VouchersResource {
    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance vouchers(List<Voucher> vouchers, DateTimeFormatter dateTimeFormatter);
    }

    @Inject
    VoucherRepository voucherRepository;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public TemplateInstance getVouchers() {
        List<Voucher> vouchers = voucherRepository.findAllWithCustomers();
        return Templates.vouchers(vouchers, dateTimeFormatter);
    }
}
