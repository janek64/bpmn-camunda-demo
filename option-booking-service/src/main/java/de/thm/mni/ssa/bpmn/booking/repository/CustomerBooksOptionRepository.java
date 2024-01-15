package de.thm.mni.ssa.bpmn.booking.repository;

import de.thm.mni.ssa.bpmn.booking.model.entity.CustomerBooksOption;
import de.thm.mni.ssa.bpmn.booking.model.entity.CustomerBooksOptionId;
import de.thm.mni.ssa.bpmn.booking.model.entity.CustomerBooksOption_;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;

import java.util.List;

@ApplicationScoped
public class CustomerBooksOptionRepository implements PanacheRepositoryBase<CustomerBooksOption, CustomerBooksOptionId> {

    @Inject
    SessionFactory sessionFactory;

    public List<CustomerBooksOption> findAllBookingsWithCustomersAndOptions() {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<CustomerBooksOption> criteriaQuery = criteriaBuilder.createQuery(CustomerBooksOption.class);
        Root<CustomerBooksOption> root = criteriaQuery.from(CustomerBooksOption.class);
        root.fetch(CustomerBooksOption_.customer);
        root.fetch(CustomerBooksOption_.option);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(CustomerBooksOption_.startTime)));
        return sessionFactory.fromSession(session ->
                session.createQuery(criteriaQuery).getResultList()
        );
    }
}
