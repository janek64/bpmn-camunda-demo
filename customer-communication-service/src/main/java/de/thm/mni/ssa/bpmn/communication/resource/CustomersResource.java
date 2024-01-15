package de.thm.mni.ssa.bpmn.communication.resource;

import de.thm.mni.ssa.bpmn.communication.repository.CustomerRepository;
import de.thm.mni.ssa.bpmn.communication.model.entity.Customer;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/customers")
public class CustomersResource {

    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance customers(List<Customer> customers);
    }

    @Inject
    CustomerRepository customerRepository;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public TemplateInstance getCustomers() {
        List<Customer> customers = customerRepository.findAllOrderByName();
        return Templates.customers(customers);
    }
}
