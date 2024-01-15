package de.thm.mni.ssa.bpmn.booking.worker;

import de.thm.mni.ssa.bpmn.booking.model.dto.GetOptionDetailsInputDto;
import de.thm.mni.ssa.bpmn.booking.model.dto.GetOptionDetailsOutputDto;
import de.thm.mni.ssa.bpmn.booking.model.entity.Option;
import de.thm.mni.ssa.bpmn.booking.model.exceptions.OptionNotFoundException;
import de.thm.mni.ssa.bpmn.booking.repository.OptionRepository;
import io.quarkiverse.zeebe.JobWorker;
import io.quarkiverse.zeebe.VariablesAsType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OptionsWorker {

    @Inject
    OptionRepository optionRepository;

    @JobWorker(type = "get-option-details")
    public GetOptionDetailsOutputDto getOptionDetails(@VariablesAsType GetOptionDetailsInputDto input) {
        Option option = optionRepository.findById(input.optionId());
        if (option == null) {
            throw new OptionNotFoundException(input.optionId());
        }
        return new GetOptionDetailsOutputDto(
                option.getName(),
                option.getDurationHours(),
                option.getPriceCent()
        );
    }
}
