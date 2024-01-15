package de.thm.mni.ssa.bpmn.booking.repository;

import de.thm.mni.ssa.bpmn.booking.model.entity.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, UUID> {
}
