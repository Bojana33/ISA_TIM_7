package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String description;

    @Column
    private Double averageRating = 0.0;

    @ElementCollection
    private Set<Integer> ratings;

    @ManyToMany(targetEntity = Dermatologist.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Dermatologist> dermatologists = new HashSet<>();

    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private Set<Pharmacist> pharmacists = new HashSet<>();

    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private Set<OrderForm> orderForms = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "Subscriptions", joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
    private Set<Patient> subscriptionedPatients = new HashSet<>();

    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private Set<PharmacyAdministrator> pharmacyAdministrators = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Drug> drugs = new HashSet<>();

    @OneToOne(mappedBy = "pharmacy", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private PharmacyReport pharmacyReport;

    public Pharmacy(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
