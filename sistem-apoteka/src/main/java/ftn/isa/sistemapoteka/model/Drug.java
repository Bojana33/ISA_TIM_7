package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Drug implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long code;

    @Column
    private String name;

    @Column
    private Double averageRating = 0.0;

    @ElementCollection
    private Set<Integer> ratings;

    @Column
    private String contraindications;

    @Column
    private String structure;

    @Column
    private Integer dailyIntake;

    @Column
    private Boolean reserved;

    @Column
    private String producer;

    @Column
    private Boolean onPrescription;

    @Column
    private String additionalNote;

    @Column
    private Integer loyaltyPoints;

    @Column
    private Integer quantity;

    @Column
    private Double price;

    @OneToMany(targetEntity = Drug.class)
    private List<Drug> replacementDrugs;

    @Column
    private DrugType drugType;

    @Column
    private DrugShape drugShape;

    public Drug(Long code, String name, String contraindications, String structure, int dailyIntake, Boolean reserved, String producer, Boolean onPrescription, String additionalNote, Integer loyaltyPoints, Integer quantity, Double price, DrugType drugType, DrugShape drugShape, List<Drug> replacementDrugs) {
        this.code = code;
        this.name = name;
        this.contraindications = contraindications;
        this.structure = structure;
        this.dailyIntake = dailyIntake;
        this.reserved = reserved;
        this.producer = producer;
        this.onPrescription = onPrescription;
        this.additionalNote = additionalNote;
        this.loyaltyPoints = loyaltyPoints;
        this.quantity = quantity;
        this.price = price;
        this.drugType = drugType;
        this.drugShape = drugShape;
        this.replacementDrugs = replacementDrugs;
    }

}
