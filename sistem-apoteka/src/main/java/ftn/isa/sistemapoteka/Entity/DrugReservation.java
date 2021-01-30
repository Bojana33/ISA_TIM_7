package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DrugReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime dateOfReservation;

    @Column
    private LocalDateTime takingDrugDate;

    @OneToOne(mappedBy = "Drug",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Drug drug;

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
