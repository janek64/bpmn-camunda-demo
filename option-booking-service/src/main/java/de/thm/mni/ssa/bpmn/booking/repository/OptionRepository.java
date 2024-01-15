package de.thm.mni.ssa.bpmn.booking.repository;

import de.thm.mni.ssa.bpmn.booking.model.entity.Option;
import de.thm.mni.ssa.bpmn.booking.model.entity.Option_;
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
public class OptionRepository implements PanacheRepositoryBase<Option, UUID> {

    @Inject
    SessionFactory sessionFactory;

    public List<Option> findAllOrderByName() {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Option> criteriaQuery = criteriaBuilder.createQuery(Option.class);
        Root<Option> root = criteriaQuery.from(Option.class);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Option_.name)));
        return sessionFactory.fromSession(session ->
                session.createQuery(criteriaQuery).getResultList()
        );
    }
}
