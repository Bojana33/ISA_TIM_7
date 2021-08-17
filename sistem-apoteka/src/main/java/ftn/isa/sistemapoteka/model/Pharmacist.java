package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("Pharmacist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacist extends User {

    @ManyToOne(targetEntity = Pharmacy.class)
    private Pharmacy pharmacy;

    @Column
    private Double averageRating;

    @ElementCollection
    private Set<Integer> ratings;



}
