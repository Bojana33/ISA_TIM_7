package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    private Double averageRating = 0.0;

    @ElementCollection
    private Set<Integer> ratings;

    @ManyToMany(targetEntity = Dermatologist.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Dermatologist> dermatologists = new HashSet<>();

    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private Set<Pharmacist> pharmacists = new HashSet<>();

    @ManyToMany( targetEntity = Patient.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Patient> subscriptionedPatients = new HashSet<>();

    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private Set<PharmacyAdministrator> pharmacyAdministrators = new HashSet<>();

    @ElementCollection
    @MapKeyColumn(name = "drug_code")
    @Column(name = "price")
    private Map<Long,Double> drugs = new HashMap<>();

    @ElementCollection
    @MapKeyColumn(name = "drug_code")
    @Column(name = "quantity")
    private Map<Long,Integer> drugsQuantity = new HashMap<>();

    public Pharmacy(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
