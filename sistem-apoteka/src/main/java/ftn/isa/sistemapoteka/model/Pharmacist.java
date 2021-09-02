package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Pharmacist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacist extends User {

    @ManyToMany(targetEntity = Pharmacy.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "PharmacyPharmacist", joinColumns = @JoinColumn(name = "pharmacist_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
    private Set<Pharmacy> pharmacies = new HashSet<>();

    @Column
    private Double averageRating;

    @ElementCollection
    private Set<Integer> ratings;

    @OneToMany(mappedBy = "pharmacist", targetEntity = Appointment.class ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();

}
