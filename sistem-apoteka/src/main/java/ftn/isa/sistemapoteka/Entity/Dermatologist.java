package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Dermatologist")
public class Dermatologist extends RegisteredUser{

    @OneToMany(mappedBy = "dermatologist", targetEntity = Appointment.class ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();

    @ManyToMany(targetEntity = Pharmacy.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "PharmDerm", joinColumns = @JoinColumn(name = "dermatologist_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
    private Set<Pharmacy> pharmacies = new HashSet<>();

    public Dermatologist() {
        super();
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(Set<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
    }
}
