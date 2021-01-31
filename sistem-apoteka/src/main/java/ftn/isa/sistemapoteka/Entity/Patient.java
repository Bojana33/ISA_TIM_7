package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Patient")
public class Patient extends RegisteredUser{

    @Column
    private Float loyaltyPoints;

    @ManyToMany(targetEntity = Drug.class)
    @JoinTable(name = "Allergies", joinColumns = @JoinColumn(name = "Patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Drug_id", referencedColumnName = "id"))
    private Set<Drug> allergies = new HashSet<>();

    @OneToMany(mappedBy = "Patient",targetEntity = Appointment.class)
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "Patient",targetEntity = Consultation.class)
    private Set<Consultation> consultations = new HashSet<>();

    @OneToMany(mappedBy = "Patient", targetEntity = Complaint.class)
    private Set<Complaint> complaints = new HashSet<>();

    @OneToMany(mappedBy = "Patient",targetEntity = eRecipe.class)
    private Set<eRecipe> eRecipes = new HashSet<>();

    @OneToMany(mappedBy = "Patient", targetEntity = DrugReservation.class)
    private Set<DrugReservation> drugReservations = new HashSet<>();

    @Column
    private UserCategory userCategory;

    @ManyToMany
    @JoinTable(name = "Subscriptions", joinColumns = @JoinColumn(name = "Patient_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "Pharmacy_id", referencedColumnName = "id"))
    private Set<Pharmacy> subscriptions = new HashSet<>();


    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Patient() {
    }


}
