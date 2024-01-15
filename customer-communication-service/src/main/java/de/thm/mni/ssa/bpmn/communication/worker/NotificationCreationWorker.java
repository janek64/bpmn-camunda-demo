package de.thm.mni.ssa.bpmn.communication.worker;

import de.thm.mni.ssa.bpmn.communication.model.dto.CreateInvalidVoucherNotificationInputDto;
import de.thm.mni.ssa.bpmn.communication.model.entity.NotificationType;
import de.thm.mni.ssa.bpmn.communication.model.exceptions.CustomerNotFoundException;
import de.thm.mni.ssa.bpmn.communication.repository.NotificationRepository;
import de.thm.mni.ssa.bpmn.communication.model.dto.CreateInsufficientCreditNotificationInputDto;
import de.thm.mni.ssa.bpmn.communication.model.dto.CreateSuccessfulBookingNotificationInputDto;
import de.thm.mni.ssa.bpmn.communication.model.entity.Customer;
import de.thm.mni.ssa.bpmn.communication.model.entity.Notification;
import de.thm.mni.ssa.bpmn.communication.repository.CustomerRepository;
import io.quarkiverse.zeebe.JobWorker;
import io.quarkiverse.zeebe.VariablesAsType;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@ApplicationScoped
public class NotificationCreationWorker {

    @Inject
    CustomerRepository customerRepository;
    @Inject
    NotificationRepository notificationRepository;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private Notification createAndPersistNotification(NotificationType notificationType, UUID customerId, String title, String message) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException(customerId);
        }
        Notification notification = new Notification();
        notification.setNotificationType(notificationType);
        notification.setCustomer(customer);
        notification.setCreationTimestamp(LocalDateTime.now(ZoneOffset.UTC));
        notification.setTitle(title);
        notification.setMessage(message);
        notificationRepository.persist(notification);
        return notification;
    }

    @Transactional
    @JobWorker(type = "create-invalid-voucher-notification")
    public void createInvalidVoucherNotification(@VariablesAsType CreateInvalidVoucherNotificationInputDto input) {
        String title = "Failed to redeem voucher code";
        String message = String.format(
                "Failed to redeem to voucher code '%s' to book the option '%s' since the voucher code is invalid or already used",
                input.voucherCode(), input.optionName()
        );
        Notification notification = createAndPersistNotification(NotificationType.ERROR, input.customerId(), title, message);
        Log.infof("Created new invalid voucher notification for customer %s (ID=%s)", input.customerId(), notification.getNotificationId());
    }

    @Transactional
    @JobWorker(type = "create-insufficient-credit-notification")
    public void createInsufficientCreditNotification(@VariablesAsType CreateInsufficientCreditNotificationInputDto input) {
        String title = "Failed to book option with balance";
        String message = String.format(
                "Failed to book the option '%s' with price %.2f€ due to insufficient balance",
                input.optionName(), input.getPriceEuro()
        );
        Notification notification = createAndPersistNotification(NotificationType.ERROR, input.customerId(), title, message);
        Log.infof("Created new insufficient balance notification for customer %s (ID=%s)", input.customerId(), notification.getNotificationId());
    }

    @Transactional
    @JobWorker(type = "create-successful-booking-notification")
    public void createSuccessfulBookingNotification(@VariablesAsType CreateSuccessfulBookingNotificationInputDto input) {
        LocalDateTime bookingStart = LocalDateTime.parse(input.bookingStart());
        LocalDateTime bookingEnd = LocalDateTime.parse(input.bookingEnd());
        String title = String.format("Booked new option '%s'", input.optionName());
        String message = String.format(
                "Booked the option '%s' starting from '%s' until '%s'",
                input.optionName(), bookingStart.format(dateTimeFormatter), bookingEnd.format(dateTimeFormatter)
        );
        Notification notification = createAndPersistNotification(NotificationType.SUCCESS, input.customerId(), title, message);
        Log.infof("Created new successful booking notification for customer %s (ID=%s)", input.customerId(), notification.getNotificationId());
    }
}
