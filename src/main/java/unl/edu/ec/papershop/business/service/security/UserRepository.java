package unl.edu.ec.papershop.business.service.security;

import jakarta.inject.Inject;
import jakarta.persistence.*;
import unl.edu.ec.papershop.business.service.CrudGenericService;
import unl.edu.ec.papershop.domain.security.User;
import jakarta.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Stateless
public class UserRepository {

    @Inject
    private CrudGenericService crudService;

    public User save(User user) {
        if (user.getId() == null){
            return crudService.create(user);
        } else {
            return crudService.update(user);
        }
    }

    public User find(Long id) throws EntityNotFoundException {
        User user = crudService.find(User.class, id);
        if (user == null){
            throw new EntityNotFoundException("User no encontrado con id [" + id + "]");
        }
        return user;
    }

    public User find(String name) throws EntityNotFoundException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        User user = crudService.findSingleWithNamedQuery("User.findLikeName", parameters);
        if (user == null){
            throw new EntityNotFoundException("User no encontrado con name [" + name + "]");
        }
        return user;
    }

    public List<User> findWithLike(String criteria) throws EntityNotFoundException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", "%" + criteria + "%");
        List<User> users = crudService.findWithNamedQuery("User.findLikeName", parameters);
        if (users == null || users.isEmpty()){
            throw new EntityNotFoundException("No se encontraron usuarios con el criterio [" + criteria + "]");
        }
        return users;
    }
}