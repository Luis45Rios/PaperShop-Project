package unl.edu.ec.papershop.business.service.security;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.Query;
import unl.edu.ec.papershop.business.service.CrudGenericService;
import unl.edu.ec.papershop.domain.security.ActionType;
import unl.edu.ec.papershop.domain.security.Permission;
import unl.edu.ec.papershop.domain.security.Role;
import unl.edu.ec.papershop.exception.EntityNotFoundException;

import java.util.*;
@Stateless
public class PermissionRepository {

    @Inject
    private CrudGenericService crudService;

    public List<Permission> findAll(){
        return crudService.findWithNativeQuery("Select * from permission", Permission.class);
    }

    public Permission find(Long id) throws EntityNotFoundException {
        Permission entity = crudService.find(Permission.class, id);
        if (entity != null){
            return entity;
        }
        throw new EntityNotFoundException("Permission not found [" + id + "]");
    }

}