package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private Float rating;

    @OneToMany(mappedBy = "dermatologists", cascade = CascadeType.ALL)
    private Set<Dermatologist> dermatologists = new HashSet<>();

    @OneToMany(mappedBy = "pharmacists", cascade = CascadeType.ALL)
    private Set<Pharmacist> pharmacists = new HashSet<>();

    @OneToMany(mappedBy = "subscriptionedPatients", cascade = CascadeType.ALL)
    private Set<Patient> subscriptionedPatients = new HashSet<>();

    @OneToMany(mappedBy = "pharmacyAdministrators", cascade = CascadeType.ALL)
    private Set<PharmacyAdministrator> pharmacyAdministrators = new HashSet<>();

    @OneToMany(mappedBy = "drugs", cascade = CascadeType.ALL)
    private Set<Drug> drugs = new HashSet<>();

}
