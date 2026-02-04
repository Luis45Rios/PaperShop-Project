package unl.edu.ec.papershop.view.security;
import unl.edu.ec.papershop.domain.security.User;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipalDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String name;
    private Set<String> roles;

    public UserPrincipalDTO(User user) {
        this.id = user.getId();
        this.username = user.getName();
        this.name = user.getName();
        this.roles = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public Set<String> getRoles() {
        return roles;
    }
}