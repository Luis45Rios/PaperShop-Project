package unl.edu.ec.papershop.view.security;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import unl.edu.ec.papershop.business.SecurityFacade;
import unl.edu.ec.papershop.domain.security.ActionType;
import unl.edu.ec.papershop.domain.security.User;


import javax.management.relation.Role;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserSession implements Serializable {

    private static final long serialVersionUID = 1L;

    private User user;

    public void postLogin(User user) {
        this.user = user;
    }

    public void logout() {
        this.user = null;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
