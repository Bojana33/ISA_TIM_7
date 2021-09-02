package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Days implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Pharmacist.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dermatologist_id", referencedColumnName = "id")
    private Dermatologist dermatologist;

    @ManyToOne(targetEntity = Pharmacist.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pharmacist_id", referencedColumnName = "id")
    private Pharmacist pharmacist;

    /*@ManyToOne(targetEntity = Pharmacy.class)
    private PharmacyAdministrator admin;*/

    @Column
    private LocalDate beggining;

    @Column
    private LocalDate end;

    @Column
    private boolean appointed;

    @Column
    private boolean vacation;

    @Column
    private boolean vacationSubmitted;

    @Column
    private boolean vacationApproved;

    @Column
    private boolean vacationReviewed;

    @Column
    private LocalTime dayBeggining;

    @Column
    private LocalTime dayEnd;


}
