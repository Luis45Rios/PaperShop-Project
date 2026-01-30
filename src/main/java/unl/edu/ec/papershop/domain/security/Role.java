package unl.edu.ec.papershop.domain.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
@NamedQueries({
        @NamedQuery(name = "Role.findByName",
                query = "SELECT r FROM Role r WHERE r.name = :name"),
        @NamedQuery(name = "Role.findAll",
                query = "SELECT r FROM Role r ORDER BY r.name")
})
public class Role implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Validaciones a nivel de vista y modelo
    @NotNull
    @NotEmpty
    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(length = 200)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    // Constructor por defecto
    public Role() {
    }

    // Constructor con parámetros
    public Role(Long id, @NotNull @NotEmpty String name) {
        this.id = id;
        this.setName(name);
    }

    public Role(Long id, @NotNull @NotEmpty String name, String description) {
        this.id = id;
        this.setName(name);
        this.description = description;
    }

    // Método para agregar un permiso
    public void addPermission(Permission permission) {
        if (permission != null) {
            if (!getPermissions().contains(permission)) {
                this.permissions.add(permission);
            }
        }
    }

    // Método para eliminar un permiso
    public void removePermission(Permission permission) {
        if (permission != null) {
            this.permissions.remove(permission);
        }
    }

    // Método para verificar si tiene un permiso específico
    public boolean hasPermission(Permission permission) {
        return this.permissions.contains(permission);
    }

    // Método para verificar si tiene permiso por path y acción
    public boolean hasPermission(String path, ActionType actionType) {
        return this.permissions.stream()
                .anyMatch(p -> p.getPath().equals(path) &&
                        (p.getActionType() == ActionType.ALL ||
                                p.getActionType() == actionType));
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

    public void setName(@NotNull @NotEmpty String name) {
        this.name = name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(getId(), role.getId()) &&
                Objects.equals(getName(), role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
