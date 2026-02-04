package unl.edu.ec.jbrew.business;

import jakarta.inject.Inject;
import unl.edu.ec.papershop.domain.security.User;
import unl.edu.ec.papershop.exception.AlreadyEntityException;
import unl.edu.ec.papershop.exception.CredentialInvalidException;
import unl.edu.ec.papershop.exception.EncryptorException;
import unl.edu.ec.papershop.business.SecurityFacade;

import static org.junit.jupiter.api.Assertions.*;

class SecurityFacadeTest {


    @org.junit.jupiter.api.Test
    void createUser() throws EncryptorException, AlreadyEntityException {
        SecurityFacade facade = new SecurityFacade();
        User user = new User(null, "papeleria-user", "12345678");
        User userCreated = facade.createUser(user);
        assertNotNull(userCreated.getId());
        assertEquals(2, userCreated.getId());
    }

    @org.junit.jupiter.api.Test
    void authenticate() throws CredentialInvalidException {
        SecurityFacade facade = new SecurityFacade();
        User user = facade.authenticate("admin", "12345678");
        System.out.println("Usuario autenticado: " + user);
        assertNotNull(user);
    }

    @org.junit.jupiter.api.Test
    void authenticateWithCredentialInvalidException() {
        SecurityFacade facade = new SecurityFacade();
        try {
            User user = facade.authenticate("admin", "1234567891011");
        } catch (CredentialInvalidException e) {
            assertNotNull(e);
        }
    }

    @org.junit.jupiter.api.Test
    void findAllRolesWithPermission() {
    }

    @org.junit.jupiter.api.Test
    void findRolesWithPermissionByUser() {
    }


}