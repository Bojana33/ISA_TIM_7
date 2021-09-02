package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends User {

    @Column
    private Double loyaltyPoints = 0.0;

    @ManyToMany(targetEntity = Drug.class)
    @JoinTable(name = "Allergies", joinColumns = @JoinColumn(name = "Patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Drug_id", referencedColumnName = "id"))
    private Set<Drug> allergies = new HashSet<>();

    @OneToMany(mappedBy = "patient",targetEntity = Appointment.class)
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "patient",targetEntity = Consultation.class)
    private Set<Consultation> consultations = new HashSet<>();

    @OneToMany(mappedBy = "patient", targetEntity = Complaint.class)
    private Set<Complaint> complaints = new HashSet<>();

    @OneToMany(mappedBy = "patient",targetEntity = eRecipe.class)
    private Set<eRecipe> eRecipes = new HashSet<>();

    @OneToMany(mappedBy = "patient", targetEntity = DrugReservation.class)
    private Set<DrugReservation> drugReservations = new HashSet<>();

    @Column
    private UserCategory userCategory = UserCategory.NONE;

    @Column
    private Integer discount;

    @ManyToMany
    @JoinTable(name = "Subscriptions", joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
    private Set<Pharmacy> subscriptions = new HashSet<>();

}
