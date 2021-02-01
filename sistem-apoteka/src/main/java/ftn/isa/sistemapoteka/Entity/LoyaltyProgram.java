package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class LoyaltyProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private float consultationPoints;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private float appointmentPoints;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private float regularPoints;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private float silverPoints;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private float goldPoints;

    @OneToMany(mappedBy = "LoyaltyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "LoyaltyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Consultation> consultations = new HashSet<>();

    @OneToMany(mappedBy = "LoyaltyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Patient> patients = new HashSet<>();


}
