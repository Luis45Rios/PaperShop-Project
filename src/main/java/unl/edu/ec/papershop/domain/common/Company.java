package unl.edu.ec.papershop.domain.common;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Company extends Organization {

    @NotNull
    @Enumerated(EnumType.STRING)
    private CompanyType type;

    public Company() {
        super();
        this.type = CompanyType.PRIVATE;
    }

    public Company(Long id,
                   @NotNull String name,
                   @NotNull LocalDate creationDate,
                   @NotNull IdentificationType identificationType,
                   @NotNull String identificationNumber,
                   @NotNull String email,
                   @NotNull CompanyType type) {
        super(id, name, creationDate, identificationType, identificationNumber, email);
        this.type = type;
    }

    public CompanyType getType() {
        return type;
    }

    public void setType(CompanyType type) {
        this.type = type;
    }
}