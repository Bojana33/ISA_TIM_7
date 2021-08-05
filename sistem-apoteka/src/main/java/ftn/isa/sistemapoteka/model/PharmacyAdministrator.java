package ftn.isa.sistemapoteka.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("pharmacy_administrator")
public class PharmacyAdministrator extends User {

    @ManyToOne(targetEntity = Pharmacy.class)
    private Pharmacy pharmacy;

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public PharmacyAdministrator() {
        super();
    }
}
