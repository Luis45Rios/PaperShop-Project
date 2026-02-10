package unl.edu.ec.papershop.view.security;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import unl.edu.ec.papershop.business.SecurityFacade;
import unl.edu.ec.papershop.domain.security.ActionType;
import unl.edu.ec.papershop.domain.security.Role;
import unl.edu.ec.papershop.domain.security.User;


import java.io.Serial;
import java.util.Set;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserSession implements java.io.Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(UserSession.class.getName());
    @Inject
    private SecurityFacade securityFacade;

    private User user;

    public void postLogin(@NotNull User user) throws EntityNotFoundException, unl.edu.ec.papershop.exception.EntityNotFoundException {
        logger.info("User logged in: " + user.getName());
        this.user = user;
        Set<Role> roles = securityFacade.findRolesWithPermissionByUser(this.user.getId());
        user.setRoles(roles);
    }

public boolean hasPermissionForPage(String pagePath) {
        // Permitir acceso a todas las páginas del dashboard
        if (pagePath.equals("/dashboard.xhtml") || 
            pagePath.equals("/sales.xhtml") || 
            pagePath.equals("/providers.xhtml") || 
            pagePath.equals("/reports.xhtml")) {
            return true;
        }
        return this.hasPermission(pagePath, ActionType.READ);
    }

public boolean hasPermission(String resource, ActionType action) {
        // Permitir acceso a todas las páginas del dashboard
        if (resource.equals("/dashboard.xhtml") || 
            resource.equals("/sales.xhtml") || 
            resource.equals("/providers.xhtml") || 
            resource.equals("/reports.xhtml")) {
            return true;
        }
        
        // Si no hay usuario, denegar acceso
        if (user == null) {
            return false;
        }
        
        // Si no hay roles, denegar acceso
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return false;
        }
        
        return user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .anyMatch(permission -> permission.matchWith(resource, action));
    }

    public boolean hasRole(@NotNull String roleName){
        if (user == null){
            return false;
        }
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(roleName));
    }

public User getUser() {
        return user;
    }

    public User getCurrentUser() {
        return user;
    }

    public void logout() {
        this.user = null;
    }

    public boolean isLoggedIn() {
        return this.user != null;
    }

}
