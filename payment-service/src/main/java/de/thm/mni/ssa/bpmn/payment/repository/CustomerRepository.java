package de.thm.mni.ssa.bpmn.payment.repository;

import de.thm.mni.ssa.bpmn.payment.model.entity.Customer;
import de.thm.mni.ssa.bpmn.payment.model.entity.Customer_;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, UUID> {

    @Inject
    SessionFactory sessionFactory;

    public List<Customer> findAllOrderByName() {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Customer_.name)));
        return sessionFactory.fromSession(session ->
                session.createQuery(criteriaQuery).getResultList()
        );
    }
}
