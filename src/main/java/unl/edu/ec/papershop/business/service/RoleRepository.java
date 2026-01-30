package unl.edu.ec.papershop.business.service;

import unl.edu.ec.papershop.domain.security.Role;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class RoleRepository {

    @PersistenceContext(unitName = "papershopPU")
    private EntityManager em;

    // Método para crear un nuevo rol
    public Role create(Role role) {
        em.persist(role);
        return role;
    }

    // Método para actualizar un rol existente
    public Role update(Role role) {
        return em.merge(role);
    }

    // Método para eliminar un rol
    public void delete(Long id) {
        Role role = findById(id);
        if (role != null) {
            em.remove(role);
        }
    }

    // Método para buscar por ID
    public Role findById(Long id) {
        return em.find(Role.class, id);
    }

    // Método para buscar por nombre
    public Role findByName(String name) {
        try {
            TypedQuery<Role> query = em.createNamedQuery("Role.findByName", Role.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    // Método para obtener todos los roles
    public List<Role> findAll() {
        TypedQuery<Role> query = em.createNamedQuery("Role.findAll", Role.class);
        return query.getResultList();
    }

    // Método para verificar si existe un rol con ese nombre
    public boolean existsByName(String name) {
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(r) FROM Role r WHERE r.name = :name", Long.class);
            query.setParameter("name", name);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Método para agregar un permiso a un rol
    public void addPermissionToRole(Long roleId, Long permissionId) {
        // Esto se haría con consultas nativas o cargando las entidades
        Role role = findById(roleId);
        if (role != null) {
            // Necesitarías obtener el Permission desde PermissionRepository
            // role.addPermission(permission);
            // update(role);
        }
    }

    // Método para remover un permiso de un rol
    public void removePermissionFromRole(Long roleId, Long permissionId) {
        Role role = findById(roleId);
        if (role != null) {
            // Similar al método anterior
        }
    }
}