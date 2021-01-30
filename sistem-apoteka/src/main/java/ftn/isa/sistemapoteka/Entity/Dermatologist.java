package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("dermatologist")
public class Dermatologist extends RegisteredUser{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "Dermatologist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "PharmDerm", joinColumns = @JoinColumn(name = "DermatologistId", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "PharmacyId", referencedColumnName = "id"))
    private Set<Pharmacy> pharmacies = new HashSet<>();



}
