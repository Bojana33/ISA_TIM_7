package ftn.isa.sistemapoteka.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pharmacy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private Float rating;

    @ManyToMany(targetEntity = Dermatologist.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Dermatologist> dermatologists = new HashSet<>();

    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private Set<Pharmacist> pharmacists = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "Subscriptions", joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
    private Set<Patient> subscriptionedPatients = new HashSet<>();

    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private Set<PharmacyAdministrator> pharmacyAdministrators = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Drug> drugs = new HashSet<>();

    public Pharmacy() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
