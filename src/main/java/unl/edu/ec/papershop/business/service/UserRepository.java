package unl.edu.ec.papershop.business.service;

import unl.edu.ec.papershop.domain.security.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UserRepository {

    @PersistenceContext(unitName = "papershopPU")
    private EntityManager em;

    public User create(User user) {
        em.persist(user);
        return user;
    }

    public User update(User user) {
        return em.merge(user);
    }

    public void delete(Long id) {
        User user = findById(id);
        if (user != null) {
            em.remove(user);
        }
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public User findByName(String name) {
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.name = :name", User.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> findAll() {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u ORDER BY u.name", User.class);
        return query.getResultList();
    }

    // Autenticación de usuario
    public User authenticate(String name, String password) {
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.name = :name AND u.password = :password",
                    User.class);
            query.setParameter("name", name);
            query.setParameter("password", password);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    // Verificar si el nombre de usuario ya existe
    public boolean existsByName(String name) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.name = :name", Long.class);
        query.setParameter("name", name);
        return query.getSingleResult() > 0;
    }
}
