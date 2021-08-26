package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
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
    private LocalDateTime dateTime;

    @ManyToOne(targetEntity = Dermatologist.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dermatologist_id", nullable = false, referencedColumnName = "id")
    private Dermatologist dermatologist;

    @ManyToOne(targetEntity = Patient.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

}
