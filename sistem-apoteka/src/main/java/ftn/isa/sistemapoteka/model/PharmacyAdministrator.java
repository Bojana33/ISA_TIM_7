package ftn.isa.sistemapoteka.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("pharmacy_administrator")
public class PharmacyAdministrator extends User {

    @ManyToOne(targetEntity = Pharmacy.class)
    private Pharmacy pharmacy;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private Set<OrderForm> created = new HashSet<>();

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
