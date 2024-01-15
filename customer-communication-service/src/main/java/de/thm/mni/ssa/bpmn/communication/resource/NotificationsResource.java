package de.thm.mni.ssa.bpmn.communication.resource;

import de.thm.mni.ssa.bpmn.communication.repository.CustomerRepository;
import de.thm.mni.ssa.bpmn.communication.model.entity.Customer;
import de.thm.mni.ssa.bpmn.communication.model.entity.Notification;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Path("/notifications/{customer-id}")
public class NotificationsResource {

    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance notifications(Customer customer, List<Notification> notifications, DateTimeFormatter dateTimeFormatter);
    }

    @Inject
    CustomerRepository customerRepository;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public TemplateInstance getNotificationsByCustomerId(@PathParam("customer-id") UUID customerId) {
        Customer customer = customerRepository.findById(customerId);
        List<Notification> notifications = customer.getNotifications().stream()
                .sorted(Comparator.comparing(Notification::getCreationTimestamp).reversed())
                .toList();
        return Templates.notifications(customer, notifications, dateTimeFormatter);
    }
}