package ftn.isa.sistemapoteka.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class DrugReservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime dateOfReservation;

    @Column
    private LocalDateTime takingDrugDate;

    @OneToOne(cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Drug drug;

    @Column
    Boolean taken;

    @ManyToOne(targetEntity = Patient.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", nullable = false, referencedColumnName = "id")
    private Patient patient;

    public DrugReservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(LocalDateTime dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public LocalDateTime getTakingDrugDate() {
        return takingDrugDate;
    }

    public void setTakingDrugDate(LocalDateTime takingDrugDate) {
        this.takingDrugDate = takingDrugDate;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }
}
