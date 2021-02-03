package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class LoyaltyProgram implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private float consultationPoints;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private float appointmentPoints;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private float regularPoints;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private float silverPoints;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private float goldPoints;

    @Column
    private int discountRegular;

    @Column
    private int discountSilver;

    @Column
    private int discountGold;


    @OneToMany(mappedBy = "LoyaltyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "LoyaltyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Consultation> consultations = new HashSet<>();

    @OneToMany(mappedBy = "LoyaltyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Patient> patients = new HashSet<>();

    public LoyaltyProgram() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getConsultationPoints() {
        return consultationPoints;
    }

    public void setConsultationPoints(float consultationPoints) {
        this.consultationPoints = consultationPoints;
    }

    public float getAppointmentPoints() {
        return appointmentPoints;
    }

    public void setAppointmentPoints(float appointmentPoints) {
        this.appointmentPoints = appointmentPoints;
    }

    public float getRegularPoints() {
        return regularPoints;
    }

    public void setRegularPoints(float regularPoints) {
        this.regularPoints = regularPoints;
    }

    public float getSilverPoints() {
        return silverPoints;
    }

    public void setSilverPoints(float silverPoints) {
        this.silverPoints = silverPoints;
    }

    public float getGoldPoints() {
        return goldPoints;
    }

    public void setGoldPoints(float goldPoints) {
        this.goldPoints = goldPoints;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(Set<Consultation> consultations) {
        this.consultations = consultations;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public int getDiscountRegular() {
        return discountRegular;
    }

    public void setDiscountRegular(int discountRegular) {
        this.discountRegular = discountRegular;
    }

    public int getDiscountSilver() {
        return discountSilver;
    }

    public void setDiscountSilver(int discountSilver) {
        this.discountSilver = discountSilver;
    }

    public int getDiscountGold() {
        return discountGold;
    }

    public void setDiscountGold(int discountGold) {
        this.discountGold = discountGold;
    }
}
