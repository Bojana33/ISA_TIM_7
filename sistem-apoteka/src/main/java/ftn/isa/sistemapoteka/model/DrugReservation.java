package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class DrugReservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigInteger reservationNumber;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfReservation;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate takingDrugDate;

    @OneToOne(cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Drug drug;

    @ManyToOne(targetEntity = Patient.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", nullable = true, referencedColumnName = "id")
    private Patient patient;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

}
