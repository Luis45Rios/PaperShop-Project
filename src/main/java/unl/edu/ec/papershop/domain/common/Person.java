package unl.edu.ec.papershop.domain.common;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person extends Organization {

    @NotNull
    @NotEmpty
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotNull
    @NotEmpty
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 20)
    private GenderType gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "mobile_phone", length = 20)
    private String mobilePhone;

    public Person() {
        super();
        gender = GenderType.FEMALE;
        setCreationDate(LocalDate.now());
    }

    public Person(Long id,
                  @NotNull @NotEmpty String firstName,
                  @NotNull @NotEmpty String lastName,
                  GenderType gender,
                  LocalDate creationDate,
                  IdentificationType identificationType,
                  String identificationNumber,
                  String email) {
        super(id, null, creationDate, identificationType, identificationNumber, email);
        setFirstName(firstName);
        setLastName(lastName);
        this.gender = gender;
        // Establecer el nombre completo como el nombre de la organización
        setName(getFullName());
    }

    private String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    // Sobrescribir setName para mantener consistencia
    @Override
    protected void setName(@NotNull @NotEmpty String name) {
        // No permitimos cambiar el nombre directamente, se genera desde firstName y lastName
        super.setName(getFullName());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull @NotEmpty String firstName) {
        this.firstName = firstName.trim().toUpperCase();
        // Actualizar el nombre completo cuando cambia el nombre
        super.setName(getFullName());
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull @NotEmpty String lastName) {
        this.lastName = lastName.trim().toUpperCase();
        // Actualizar el nombre completo cuando cambia el apellido
        super.setName(getFullName());
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", identificationNumber='" + getIdentificationNumber() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}