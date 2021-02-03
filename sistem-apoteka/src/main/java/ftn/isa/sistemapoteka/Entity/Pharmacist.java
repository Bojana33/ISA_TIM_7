package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Pharmacist")
public class Pharmacist extends RegisteredUser{

    @ManyToOne(targetEntity = Pharmacy.class)
    private Pharmacy pharmacy;

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Pharmacist() {
        super();
    }
}
