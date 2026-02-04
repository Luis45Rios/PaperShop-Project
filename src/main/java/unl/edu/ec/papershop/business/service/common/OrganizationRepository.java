package unl.edu.ec.papershop.business.service.common;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import unl.edu.ec.papershop.business.service.CrudGenericService;
import unl.edu.ec.papershop.domain.common.Organization;

@Stateless
public class OrganizationRepository {

    @Inject
    private CrudGenericService crudService;

    public <T extends Organization> T save(T organization) {
        if (organization.getId() == null){
            return crudService.create(organization);
        } else {
            return crudService.update(organization);
        }
    }

    public <T extends Organization> T find(Class<T> type, Long id) throws EntityNotFoundException {

        T organization = crudService.find(type, id);
        if (organization == null){
            throw new EntityNotFoundException("Organization no encontrada con [" + id + "]");
        }
        return organization;
    }

}