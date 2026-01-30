package unl.edu.ec.papershop.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "companies")
public class Company extends Organization {

    @Column(name = "business_name", length = 200)
    private String businessName;

    @Column(name = "trade_name", length = 200)
    private String tradeName;

    @Column(name = "commercial_activity", length = 300)
    private String commercialActivity;

    @Column(name = "establishment_number")
    private Integer establishmentNumber;

    @Column(name = "accounting_required")
    private Boolean accountingRequired = false;

    public Company() {
        super();
    }

    public Company(Long id, String name, String identificationNumber, String email) {
        super(id, name, java.time.LocalDate.now(), IdentificationType.RUC, identificationNumber, email);
    }

    // Getters y Setters específicos de Company
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getCommercialActivity() {
        return commercialActivity;
    }

    public void setCommercialActivity(String commercialActivity) {
        this.commercialActivity = commercialActivity;
    }

    public Integer getEstablishmentNumber() {
        return establishmentNumber;
    }

    public void setEstablishmentNumber(Integer establishmentNumber) {
        this.establishmentNumber = establishmentNumber;
    }

    public Boolean getAccountingRequired() {
        return accountingRequired;
    }

    public void setAccountingRequired(Boolean accountingRequired) {
        this.accountingRequired = accountingRequired;
    }
}