package de.thm.mni.ssa.bpmn.booking.resource;

import de.thm.mni.ssa.bpmn.booking.model.entity.Option;
import de.thm.mni.ssa.bpmn.booking.repository.OptionRepository;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/options")
public class OptionsResource {

    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance options(List<Option> options);
    }

    @Inject
    OptionRepository optionRepository;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public TemplateInstance getOptions() {
        List<Option> options = optionRepository.findAllOrderByName();
        return Templates.options(options);
    }
}
