package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyReport implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private Double averageRating = 0.0;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;

   /*@ElementCollection
    private Set<Integer> ratings;*/

    @Column
    private Double pharmacistAverageRating = 0.0;

    @Column
    private Double dermatologistAverageRating = 0.0;

    @Column
    @ElementCollection
    private List<Integer> monthlyAppointments;

    @Column
    @ElementCollection
    private List<Integer> quaterlyAppointments;

    @Column
    @ElementCollection
    private List<Integer> yearlyAppointments;

    @Column
    @ElementCollection
    private List<Integer> monthlyDrugConsumption;

    @Column
    @ElementCollection
    private List<Integer> quaterlyDrugConsumption;

    @Column
    @ElementCollection
    private List<Integer> yearlyDrugConsumption;

    @Column
    @ElementCollection
    private List<Double> dailyIncome;


}
