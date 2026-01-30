package unl.edu.ec.papershop.domain.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import unl.edu.ec.papershop.domain.common.Organization;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Validaciones a nivel de vista y modelo
    @NotNull
    @NotEmpty
    @Size(min = 5)
    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String password;

    // Relationships
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public User() {
        roles = new HashSet<>();
    }

    public User(Long id,
                @NotNull @NotEmpty String name,
                @NotNull @NotEmpty String password) throws IllegalArgumentException {
        this();
        this.id = id;
        validateNameRestriction(name);
        this.setName(name);
        this.setPassword(password);
    }

    public User(Long id,
                @NotNull @NotEmpty String name,
                @NotNull @NotEmpty String password,
                @NotNull Organization organization) {
        this(id, name, password);
        this.organization = organization;
    }

    private void validateNameRestriction(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Valor requerido");
        }

        if (name.trim().length() < 5) {
            throw new IllegalArgumentException("Valor debe superar los 5 caracteres");
        }
    }

    // Método para verificar si tiene un rol específico
    public boolean hasRole(String roleName) {
        return roles.stream()
                .anyMatch(role -> role.getName().equals(roleName));
    }

    // Método para verificar si es administrador
    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    // Método para verificar si es vendedor
    public boolean isSeller() {
        return hasRole("SELLER");
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull @NotEmpty @Size(min = 5) String name) {
        this.name = name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @NotEmpty String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getName(), user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}