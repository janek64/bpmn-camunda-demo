package de.thm.mni.ssa.bpmn.booking.worker;

import de.thm.mni.ssa.bpmn.booking.model.dto.BookOptionInputDto;
import de.thm.mni.ssa.bpmn.booking.model.dto.BookOptionOutputDto;
import de.thm.mni.ssa.bpmn.booking.model.entity.Customer;
import de.thm.mni.ssa.bpmn.booking.model.entity.CustomerBooksOption;
import de.thm.mni.ssa.bpmn.booking.model.entity.Option;
import de.thm.mni.ssa.bpmn.booking.model.exceptions.CustomerNotFoundException;
import de.thm.mni.ssa.bpmn.booking.model.exceptions.OptionNotFoundException;
import de.thm.mni.ssa.bpmn.booking.repository.CustomerBooksOptionRepository;
import de.thm.mni.ssa.bpmn.booking.repository.CustomerRepository;
import de.thm.mni.ssa.bpmn.booking.repository.OptionRepository;
import io.quarkiverse.zeebe.JobWorker;
import io.quarkiverse.zeebe.VariablesAsType;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ApplicationScoped
public class BookingsWorker {
    @Inject
    CustomerRepository customerRepository;
    @Inject
    OptionRepository optionRepository;
    @Inject
    CustomerBooksOptionRepository customerBooksOptionRepository;

    @Transactional
    @JobWorker(type = "book-option")
    public BookOptionOutputDto bookOption(@VariablesAsType BookOptionInputDto input) {
        Customer customer = customerRepository.findById(input.customerId());
        if (customer == null) {
            throw new CustomerNotFoundException(input.customerId());
        }
        Option option = optionRepository.findById(input.optionId());
        if (option == null) {
            throw new OptionNotFoundException(input.optionId());
        }
        LocalDateTime startTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime endTime = startTime.plusHours(option.getDurationHours());
        CustomerBooksOption customerBooksOption = new CustomerBooksOption();
        customerBooksOption.setCustomer(customer);
        customerBooksOption.setOption(option);
        customerBooksOption.setStartTime(startTime);
        customerBooksOption.setEndTime(endTime);
        customerBooksOptionRepository.persist(customerBooksOption);
        Log.infof("Created new booking for customer '%s' and option '%s'", customer.getCustomerId(), option.getOptionId());
        return new BookOptionOutputDto(
                // Using strings is required since quarkus-zeebe does not support ObjectMapper customization
                customerBooksOption.getStartTime().toString(),
                customerBooksOption.getEndTime().toString()
        );
    }
}
