package de.thm.mni.ssa.bpmn.payment.repository;

import de.thm.mni.ssa.bpmn.payment.model.entity.Voucher;
import de.thm.mni.ssa.bpmn.communication.model.entity.Voucher_;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class VoucherRepository implements PanacheRepositoryBase<Voucher, UUID> {

    @Inject
    SessionFactory sessionFactory;

    public List<Voucher> findAllWithCustomers() {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Voucher> criteriaQuery = criteriaBuilder.createQuery(Voucher.class);
        Root<Voucher> root = criteriaQuery.from(Voucher.class);
        root.fetch(Voucher_.redeemCustomer, JoinType.LEFT);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Voucher_.voucherId)));
        return sessionFactory.fromSession(session ->
                session.createQuery(criteriaQuery).getResultList()
        );
    }

    public Voucher findByCodeWithCustomer(String voucherCode) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Voucher> criteriaQuery = criteriaBuilder.createQuery(Voucher.class);
        Root<Voucher> root = criteriaQuery.from(Voucher.class);
        root.fetch(Voucher_.redeemCustomer, JoinType.LEFT);
        criteriaQuery.where(criteriaBuilder.equal(root.get(Voucher_.code), voucherCode));
        return sessionFactory.fromSession(session ->
                session.createQuery(criteriaQuery).getSingleResultOrNull()
        );
    }
}
