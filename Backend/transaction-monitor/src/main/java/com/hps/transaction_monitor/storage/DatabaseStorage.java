package com.hps.transaction_monitor.storage;
//class to handle persistence of TransactionInfo and DetectionAlert entities to NeonDB

import com.hps.transaction_monitor.model.DetectionAlert;
import com.hps.transaction_monitor.model.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DatabaseStorage {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void saveEntity(Object entity) {
        em.persist(entity);
    }

    @Transactional
    public <T> List<T> getEntities(Class<T> clazz , String field , String value) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        var root = query.from(clazz);
        if (field != null && value != null) {
            query.where(cb.equal(root.get(field), value));
        }
        return em.createQuery(query).getResultList();
    }

    @Transactional(readOnly = true)
    public List<DetectionAlert> findAllAlerts() {
        return em.createQuery("SELECT a FROM DetectionAlert a", DetectionAlert.class).getResultList();
    }

    @Transactional(readOnly = true)
    public List<Transaction> findAllTransactions() {
        return em.createQuery("SELECT t FROM Transaction t", Transaction.class).getResultList();
    }
}
