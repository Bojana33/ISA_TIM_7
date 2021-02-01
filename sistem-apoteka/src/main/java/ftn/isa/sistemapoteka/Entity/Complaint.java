package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;

@Entity
public class Complaint {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;
}
