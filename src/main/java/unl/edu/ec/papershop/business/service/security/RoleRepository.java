package unl.edu.ec.papershop.business.service.security;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import unl.edu.ec.papershop.business.service.CrudGenericService;
import unl.edu.ec.papershop.domain.security.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Stateless
public class RoleRepository {

    @Inject
    private CrudGenericService crudService;

    // Guardar o actualizar
    public Role save(Role role) {
        if (role.getId() == null) {
            return crudService.create(role);
        } else {
            return crudService.update(role);
        }
    }

    // Buscar por ID
    public Role find(Long id) throws EntityNotFoundException {
        Role role = crudService.find(Role.class, id);
        if (role == null) {
            throw new EntityNotFoundException("Role no encontrado con id [" + id + "]");
        }
        return role;
    }

    // Buscar por nombre (usando NamedQuery si está definida, si no, JPQL)
    public Role find(String name) throws EntityNotFoundException {
        // Intentamos usar NamedQuery, si no está definida, usamos JPQL
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        Role role;
        try {
            role = crudService.findSingleWithNamedQuery("Role.findByName", parameters);
        } catch (Exception e) {
            // Si no existe la NamedQuery, usamos JPQL
            String jpql = "SELECT r FROM Role r WHERE r.name = :name";
            List<Role> roles = crudService.findWithQuery(jpql, parameters);
            if (roles.isEmpty()) {
                role = null;
            } else {
                role = roles.get(0);
            }
        }
        if (role == null) {
            throw new EntityNotFoundException("Role no encontrado con name [" + name + "]");
        }
        return role;
    }

    // Eliminar por ID
    public void delete(Long id) throws EntityNotFoundException {
        Role role = find(id);
        crudService.delete(Role.class, role.getId());
    }

    // Encontrar todos los roles
    public List<Role> findAll() {
        return crudService.findWithNamedQuery("Role.findAll");
    }

    // Encontrar todos los roles con permisos (usando EntityGraph)
    public Set<Role> findAllWithPermissions() {

        String jpql = "SELECT DISTINCT r FROM Role r LEFT JOIN FETCH r.permissions";
        List<Role> roles = crudService.findWithQuery(jpql);
        return new java.util.HashSet<>(roles);
    }

    // Buscar roles por criterio (like en el nombre)
    public List<Role> findWithCriteria(String criteria) {
        String jpql = "SELECT r FROM Role r WHERE LOWER(r.name) LIKE LOWER(:criteria) ORDER BY r.name";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("criteria", "%" + criteria + "%");
        return crudService.findWithQuery(jpql, parameters);
    }

    // Buscar roles por recurso de permiso
    public List<Role> findByPermissionResource(String resource) {
        String jpql = "SELECT DISTINCT r FROM Role r JOIN r.permissions p WHERE p.resource = :resource";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("resource", resource);
        return crudService.findWithQuery(jpql, parameters);
    }

    // Buscar roles por usuario
    public Set<Role> findByUserId(Long userId) {
        String jpql = "SELECT DISTINCT r FROM User u JOIN u.roles r WHERE u.id = :userId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", userId);
        List<Role> roles = crudService.findWithQuery(jpql, parameters);
        return new java.util.HashSet<>(roles);
    }

    // Contar todos los roles
    public Long countAll() {
        String jpql = "SELECT COUNT(r) FROM Role r";
        List<Long> result = crudService.findWithQuery(jpql);
        return result.isEmpty() ? 0L : result.get(0);
    }

    // Verificar si existe un rol por nombre
    public boolean existsByName(String name) {
        try {
            find(name);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    // Encontrar roles paginados
    public List<Role> findPaginated(int page, int pageSize) {
        return crudService.findWithNamedQuery("Role.findAll", page, pageSize);
    }
}