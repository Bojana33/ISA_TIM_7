package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consultation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double loyaltyPoints;

    @ManyToOne(targetEntity = Patient.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", nullable = false, referencedColumnName = "id")
    private Patient patient;

    @ManyToOne(targetEntity = Pharmacist.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pharmacist_id", nullable = false, referencedColumnName = "id")
    private Pharmacist pharmacist;

    @Column
    private Double price;

    @Column
    private LocalDateTime dateTime;

    @ManyToOne(targetEntity = Pharmacy.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pharmacy_id", nullable = false, referencedColumnName = "id")
    private Pharmacy pharmacy;
}
