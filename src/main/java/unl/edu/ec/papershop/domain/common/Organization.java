package unl.edu.ec.papershop.domain.common;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@MappedSuperclass
public abstract class Organization implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(nullable = false, length = 200)
    private String name;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "identification_type", nullable = false)
    private IdentificationType identificationType;

    @NotNull
    @NotEmpty
    @Column(name = "identification_number", nullable = false, length = 13)
    private String identificationNumber;

    @NotNull
    @NotEmpty
    @Email(message = "Formato de email incorrecto")
    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(length = 300)
    private String address;

    @Column(name = "legal_representative", length = 150)
    private String legalRepresentative;

    public Organization() {
        setIdentificationType(IdentificationType.RUC); // Para papelería, por defecto RUC
        setCreationDate(LocalDate.now());
    }

    public Organization(Long id,
                        String name,
                        @NotNull LocalDate creationDate,
                        @NotNull IdentificationType identificationType,
                        @NotNull @NotEmpty String identificationNumber,
                        @NotNull @NotEmpty String email) {
        this.id = id;
        this.setName(name);
        this.setCreationDate(creationDate);
        this.setIdentificationType(identificationType);
        this.setIdentificationNumber(identificationNumber);
        this.setEmail(email);
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

    protected void setName(@NotNull @NotEmpty String name) {
        this.name = name.trim();
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public IdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(@NotNull IdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(@NotNull @NotEmpty String identificationNumber) {
        this.identificationNumber = identificationNumber.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @NotEmpty String email) {
        this.email = email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getIdentificationNumber(), that.getIdentificationNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getIdentificationNumber());
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", identificationType=" + identificationType +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}