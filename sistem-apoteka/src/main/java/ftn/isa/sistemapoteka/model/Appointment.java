package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double loyaltyPoints;

    @Column
    private Double price;

    @Column
    private LocalDateTime dateTimeStart;

    @Column
    private LocalDateTime dateTimeEnd;

    @Column
    private Boolean scheduled = false;

    @Column
    private Boolean advising;

    @ManyToOne(targetEntity = Dermatologist.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dermatologist_id", nullable = true, referencedColumnName = "id")
    private Dermatologist dermatologist;

    @ManyToOne(targetEntity = Pharmacist.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pharmacist_id", nullable = true, referencedColumnName = "id")
    private Pharmacist pharmacist;

    @ManyToOne(targetEntity = Patient.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", nullable = true, referencedColumnName = "id")
    private Patient patient;

    @ManyToOne(targetEntity = Pharmacy.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pharmacy_id", nullable = true, referencedColumnName = "id")
    private Pharmacy pharmacy;

}
