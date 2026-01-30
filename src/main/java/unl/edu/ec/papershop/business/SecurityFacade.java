package unl.edu.ec.papershop.business;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import unl.edu.ec.papershop.business.service.RoleRepository;
import unl.edu.ec.papershop.business.service.UserRepository;
import unl.edu.ec.papershop.domain.security.Role;
import unl.edu.ec.papershop.domain.security.User;
import unl.edu.ec.papershop.exception.AlreadyEntityException;
import unl.edu.ec.papershop.exception.CredentialInvalidException;
import unl.edu.ec.papershop.exception.EncryptorException;
import unl.edu.ec.papershop.util.EncryptorManager;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Stateless
public class SecurityFacade implements Serializable {

    @Inject
    private UserRepository userRepository;

    @Inject
    private RoleRepository roleRepository;

    public User createUser(User user) throws EncryptorException, AlreadyEntityException {
        // Verificar si el usuario ya existe
        if (userRepository.findByName(user.getName()) != null) {
            throw new AlreadyEntityException("Usuario ya existe");
        }

        // Encriptar la contraseña
        String pwdEncripted = EncryptorManager.encrypt(user.getPassword());
        user.setPassword(pwdEncripted);

        // Guardar el usuario
        return userRepository.create(user);
    }

    public User registerEmployee(User user, String roleName) throws EncryptorException, AlreadyEntityException {
        // Verificar si el usuario ya existe
        if (userRepository.findByName(user.getName()) != null) {
            throw new AlreadyEntityException("Empleado ya existe");
        }

        // Encriptar la contraseña
        String pwdEncripted = EncryptorManager.encrypt(user.getPassword());
        user.setPassword(pwdEncripted);

        // Asignar rol (ADMIN o SELLER)
        Role role = roleRepository.findByName(roleName);
        if (role != null) {
            user.getRoles().add(role);
        }

        // Guardar el usuario
        return userRepository.create(user);
    }

    public User authenticate(String name, String password) throws CredentialInvalidException {
        try {
            // Buscar usuario por nombre
            User userFound = userRepository.findByName(name);
            if (userFound == null) {
                throw new CredentialInvalidException();
            }

            // Encriptar la contraseña proporcionada
            String pwdEncrypted = EncryptorManager.encrypt(password);

            // Comparar contraseñas
            if (pwdEncrypted.equals(userFound.getPassword())) {
                return userFound;
            }

            throw new CredentialInvalidException();
        } catch (EncryptorException e) {
            throw new CredentialInvalidException("Error en autenticación", e);
        }
    }

    public Set<Role> findAllRolesWithPermission() {
        // Obtener todos los roles
        return new LinkedHashSet<>(roleRepository.findAll());
    }

    public Set<Role> findRolesWithPermissionByUser(Long userId) throws EntityNotFoundException {
        // Buscar usuario por ID
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }

        // Devolver roles del usuario
        return user.getRoles();
    }

    public User assignRoleToUser(Long userId, String roleName) throws EntityNotFoundException {
        // Buscar usuario por ID
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }

        // Buscar rol por nombre
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new EntityNotFoundException("Rol no encontrado");
        }

        // Asignar rol al usuario
        user.getRoles().clear(); // Limpiar roles existentes
        user.getRoles().add(role);

        // Actualizar usuario
        return userRepository.update(user);
    }
}