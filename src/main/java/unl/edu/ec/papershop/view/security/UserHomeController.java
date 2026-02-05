package unl.edu.ec.papershop.view.security;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unl.edu.ec.papershop.business.SecurityFacade;
import unl.edu.ec.papershop.domain.common.Person;
import unl.edu.ec.papershop.domain.security.User;
import unl.edu.ec.papershop.exception.EntityNotFoundException;
import unl.edu.ec.papershop.faces.FacesUtil;
import unl.edu.ec.papershop.util.EncryptorManager;


import java.io.Serial;
import java.util.logging.Logger;

@Named(value = "userHome")
@ViewScoped
public class UserHomeController implements java.io.Serializable{

    private static Logger logger = Logger.getLogger(UserHomeController.class.getName());

    @Serial
    private static final long serialVersionUID = 1L;

    private Long selectedUserId;

    private User user;

    @Inject
    SecurityFacade securityFacade;


    public UserHomeController() {
    }

    public void loadUser() throws EntityNotFoundException {
        logger.info("Loading user with id: " + selectedUserId);
        if (selectedUserId != null) {
            user = securityFacade.findUser(selectedUserId);
        } else {
            user = new User();
        }
        // Provisional, debe recuperarse desde la BD
        if (user.getOrganization() == null) {
            user.setOrganization(new Person());
        }
        decryptPassword(user);
    }

    private void decryptPassword(User user){
        String pwdDecrypted = null;
        try {
            if (user.getPassword() != null && !user.getPassword().isEmpty()){
                logger.info("Password no nulo y no vacio: " + user.getPassword());
                pwdDecrypted = EncryptorManager.decrypt(user.getPassword());
                user.setPassword(pwdDecrypted);
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesUtil.addErrorMessage(e.getMessage(), "Invconveniente al decifrar la clave: " + e.getMessage());
        }

    }

    public String create() {
        try {
            user = securityFacade.createUser(user);
            //decryptPassword(user);
            FacesUtil.addSuccessMessageAndKeep("Usuario creado correctamente");
            return "userList?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Inconveniente al crear usuario: " + e.getMessage());
            return null;
        }
    }

    public String update() {
        try {
            securityFacade.updateUser(user);
            //decryptPassword(user);
            FacesUtil.addSuccessMessageAndKeep("Usuario actualizado correctamente");
            return "userList?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Inconveniente al actualizar usuario: " + e.getMessage());
            return null;
        }
    }

    public boolean isManaged(){
        return this.user.getId() != null;
    }

    public Long getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

