package unl.edu.ec.papershop.business.service.security;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import unl.edu.ec.papershop.business.service.CrudGenericService;
import unl.edu.ec.papershop.domain.security.User;
import jakarta.ejb.Stateless;
import unl.edu.ec.papershop.exception.EntityNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class UserRepository {

    @Inject
    private CrudGenericService crudService;

    public User save(User user){
        if (user.getId() == null){
            return crudService.create(user);
        } else {
            return crudService.update(user);
        }
    }

    public User find(@NotNull Long id) throws EntityNotFoundException {
        User user = crudService.find(User.class, id);
        if (user == null){
            throw new EntityNotFoundException("User no encontrado con [" + id + "]");
        }
        return user;
    }

    public User find(@NotNull String name) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        User userFound = crudService.findSingleResultOrNullWithNamedQuery("User.findLikeName", params);
        if (userFound == null){
            throw new EntityNotFoundException("User no encontrado con [" + name + "]");
        }
        return userFound;
    }

    public List<User> findWithLike(@NotNull String name) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("name", "%" + name + "%");
        return crudService.findWithNamedQuery("User.findLikeName", params);
    }
}