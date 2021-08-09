package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Dermatologist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dermatologist extends User {

    @OneToMany(mappedBy = "dermatologist", targetEntity = Appointment.class ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();

    @Column
    private Double averageRating;

    @ElementCollection
    private Set<Integer> ratings;

    @ManyToMany(targetEntity = Pharmacy.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "PharmDerm", joinColumns = @JoinColumn(name = "dermatologist_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
    private Set<Pharmacy> pharmacies = new HashSet<>();
}
