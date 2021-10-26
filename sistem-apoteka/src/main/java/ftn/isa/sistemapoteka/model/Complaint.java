package ftn.isa.sistemapoteka.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Complaint implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String text;

    @ManyToOne(targetEntity = Patient.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patientId")
    private Patient patient;

    public Complaint() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
