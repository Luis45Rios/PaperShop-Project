package unl.edu.ec.papershop.view;

import jakarta.faces.view.ViewScoped;
import jakarta.servlet.ServletException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import unl.edu.ec.papershop.business.SecurityFacade;
import unl.edu.ec.papershop.domain.security.User;
import unl.edu.ec.papershop.exception.CredentialInvalidException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import unl.edu.ec.papershop.faces.FacesUtil;
import unl.edu.ec.papershop.view.security.UserPrincipalDTO;
import unl.edu.ec.papershop.view.security.UserSession;
import unl.edu.ec.papershop.exception.EntityNotFoundException;

import java.io.Serializable;
import java.util.logging.Logger;

@Named
@ViewScoped
public class AuthenticationController implements java.io.Serializable {

    private static Logger logger = Logger.getLogger(AuthenticationController.class.getName());

    @NotNull
    @NotEmpty
    @Size(min = 5, message = "Nombre de usuario muy corto")
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 8, message = "Contraseña muy corta")
    private String password;

    @Inject
    private SecurityFacade securityFacade;

    @Inject
    private UserSession userSession;

    public String login() throws EntityNotFoundException {
        logger.info("Intento de login para usuario: " + username);
        try {
            User user = securityFacade.authenticate(username, password);
            setHttpSession(user);
            FacesUtil.addSuccessMessageAndKeep("Aviso", "Bienvenido " + user.getName());
            userSession.postLogin(user);
            return "/dashboard.xhtml?faces-redirect=true";

        } catch (CredentialInvalidException e) {
            FacesUtil.addErrorMessage("Inconveniente", e.getMessage());
            return null;
        }
    }

    /**
     * Establece la sesión de usuario en el contexto HTTP de la aplicación
     * @param user
     */
    private void setHttpSession(User user) {
        FacesContext context = FacesContext.getCurrentInstance();
        UserPrincipalDTO userPrincipal = new UserPrincipalDTO(user);
        context.getExternalContext().getSessionMap().put("user", userPrincipal);
    }

    public String logout() throws ServletException {
        FacesUtil.addSuccessMessageAndKeep(userSession.getUser().getName(), "Hasta pronto");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().invalidateSession();
        ((jakarta.servlet.http.HttpServletRequest) facesContext.getExternalContext().getRequest()).logout();
        return "/index.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}