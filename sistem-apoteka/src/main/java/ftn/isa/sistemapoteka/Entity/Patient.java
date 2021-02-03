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
    private UserCategory userCategory;

    @Column
    private int discount;

    @ManyToMany
    @JoinTable(name = "Subscriptions", joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
    private Set<Pharmacy> subscriptions = new HashSet<>();


    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Patient() {
    }

    public Float getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Float loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Set<Drug> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<Drug> allergies) {
        this.allergies = allergies;
    }

    public Set<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(Set<Consultation> consultations) {
        this.consultations = consultations;
    }

    public Set<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(Set<Complaint> complaints) {
        this.complaints = complaints;
    }

    public Set<eRecipe> geteRecipes() {
        return eRecipes;
    }

    public void seteRecipes(Set<eRecipe> eRecipes) {
        this.eRecipes = eRecipes;
    }

    public Set<DrugReservation> getDrugReservations() {
        return drugReservations;
    }

    public void setDrugReservations(Set<DrugReservation> drugReservations) {
        this.drugReservations = drugReservations;
    }

    public UserCategory getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(UserCategory userCategory) {
        this.userCategory = userCategory;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Set<Pharmacy> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Pharmacy> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
