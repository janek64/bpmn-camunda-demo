package de.thm.mni.ssa.bpmn.booking.resource;

import de.thm.mni.ssa.bpmn.booking.model.entity.CustomerBooksOption;
import de.thm.mni.ssa.bpmn.booking.repository.CustomerBooksOptionRepository;
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

@Path("/bookings")
public class BookingsResource {

    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance bookings(List<CustomerBooksOption> bookings, DateTimeFormatter dateTimeFormatter);
    }

    @Inject
    CustomerBooksOptionRepository customerBooksOptionRepository;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public TemplateInstance getBookings() {
        List<CustomerBooksOption> bookings = customerBooksOptionRepository.findAllBookingsWithCustomersAndOptions();
        return Templates.bookings(bookings, dateTimeFormatter);
    }
}
