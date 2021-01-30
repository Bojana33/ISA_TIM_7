package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class LoyaltyProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private float consultationPoints;

    @Column
    private float appointmentPoints;

    @Column
    private float regularPoints;

    @Column
    private float silverPoints;

    @Column
    private float goldPoints;

    @OneToMany(mappedBy = "LoyaltyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "LoyaltyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Consultation> consultations = new HashSet<>();

    @OneToMany(mappedBy = "LoyaltyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Patient> patients = new HashSet<>();


}
