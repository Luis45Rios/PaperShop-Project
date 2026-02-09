package unl.edu.ec.papershop.business.service;

import jakarta.persistence.PersistenceContext;
import unl.edu.ec.papershop.domain.Supplier;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class SupplierService {

    @PersistenceContext
    private EntityManager em;

    /* ===== LISTAR ===== */
    public List<Supplier> findAll() {
        return em.createQuery(
                "SELECT s FROM Supplier s ORDER BY s.name",
                Supplier.class
        ).getResultList();
    }

    /* ===== BUSCAR ===== */
    public List<Supplier> search(String text) {

        if (text == null || text.trim().isEmpty()) {
            return findAll();
        }

        return em.createQuery(
                        "SELECT s FROM Supplier s " +
                                "WHERE LOWER(s.name) LIKE :text " +
                                "   OR LOWER(s.email) LIKE :text " +
                                "ORDER BY s.name",
                        Supplier.class
                )
                .setParameter("text", "%" + text.toLowerCase() + "%")
                .getResultList();
    }

    /* ===== GUARDAR ===== */
    @Transactional
    public void save(Supplier supplier) {
        if (supplier.getId() == null) {
            em.persist(supplier);
        } else {
            em.merge(supplier);
        }
    }

    /* ===== ELIMINAR ===== */
    @Transactional
    public void delete(Supplier supplier) {
        Supplier managed = em.find(Supplier.class, supplier.getId());
        if (managed != null) {
            em.remove(managed);
        }
    }
}
