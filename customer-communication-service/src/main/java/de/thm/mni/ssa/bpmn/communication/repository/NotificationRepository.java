package de.thm.mni.ssa.bpmn.communication.repository;

import de.thm.mni.ssa.bpmn.communication.model.entity.Notification;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class NotificationRepository implements PanacheRepositoryBase<Notification, UUID> {
}
