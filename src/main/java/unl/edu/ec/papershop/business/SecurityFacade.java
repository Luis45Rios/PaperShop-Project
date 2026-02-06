package unl.edu.ec.papershop.business;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import unl.edu.ec.papershop.business.service.security.RoleRepository;
import unl.edu.ec.papershop.business.service.security.UserRepository;
import unl.edu.ec.papershop.domain.security.Role;
import unl.edu.ec.papershop.domain.security.User;
import unl.edu.ec.papershop.exception.AlreadyEntityException;
import unl.edu.ec.papershop.exception.CredentialInvalidException;
import unl.edu.ec.papershop.exception.EncryptorException;
import unl.edu.ec.papershop.exception.EntityNotFoundException;
import unl.edu.ec.papershop.util.EncryptorManager;


import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class SecurityFacade implements Serializable {

    @Inject
    private UserRepository userRepository;

    @Inject
    private RoleRepository roleRepository;

    public User createUser(User user) throws EncryptorException, AlreadyEntityException {
        String pwdEncripted = EncryptorManager.encrypt(user.getPassword());
        user.setPassword(pwdEncripted);
        User userFound = null;
        try {
            userFound = userRepository.find(user.getName());
        } catch (EntityNotFoundException e) {
            return userRepository.save(user);
        }
        throw new AlreadyEntityException("Usuario ya existe");
    }

    public User updateUser(User user) throws AlreadyEntityException, EncryptorException, EntityNotFoundException {
        if (user.getId() == null){
            return createUser(user);
        }
        String pwdEncrypted = EncryptorManager.encrypt(user.getPassword());
        user.setPassword(pwdEncrypted);
        User userFound = userRepository.find(user.getName());
        if (userFound != null && !userFound.getId().equals(user.getId())){
            throw new AlreadyEntityException("Ya existe otro usuario con ese nombre");
        }
        return userRepository.save(user);
    }

    public User authenticate(String name, String password) throws CredentialInvalidException {
        try {
            User userFound = userRepository.find(name);
            if (userFound == null){
                throw new CredentialInvalidException();
            }
            String pwdEncrypted = EncryptorManager.encrypt(password);
            if (pwdEncrypted.equals(userFound.getPassword())){
                return userFound;
            }
            throw new CredentialInvalidException();
        } catch (EncryptorException | EntityNotFoundException e) {
            throw new CredentialInvalidException("Credenciales incorrectas", e);
        }
    }

    public Set<Role> findAllRolesWithPermission(){
        return roleRepository.findAllWithPermissions();
    }

    public Set<Role> findRolesWithPermissionByUser(Long userId) throws EntityNotFoundException {
        User user = userRepository.find(userId);
        // Simulación de usuarios con rol ADMIN
        Role  role = roleRepository.find("ADMIN");
        Set<Role> roles = new LinkedHashSet<>();
        roles.add(role);
        return roles;
    }


    public List<User> findUsers(String criteria) throws EntityNotFoundException {
        return userRepository.findWithLike(criteria);
    }

    public User findUser(Long userId) throws EntityNotFoundException {
        return  userRepository.find(userId);
    }



}
