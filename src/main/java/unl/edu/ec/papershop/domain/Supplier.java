package unl.edu.ec.papershop.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "suppliers")
public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String phone;

    @Column(unique = true)
    private String email;

    public Supplier() {
        this.email = "ali@gmail.com";
        this.name = "Ali";
        this.phone = "+91123456789";
    }

    /* ===== Getters & Setters ===== */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Supplier{");
        sb.append("email='").append(email).append('\'');
        sb.append(", id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
