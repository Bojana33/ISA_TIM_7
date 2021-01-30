package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import javax.swing.text.StyledEditorKit;

@Entity
public class Drug {

    @Id
    private Long code;

    @Column
    private String name;

    @Column
    private String contraindications;

    @Column
    private String structure;

    @Column
    private int dailyIntake;

    @Column
    private Boolean reserved;

    @Column
    private String producer;

    @Column
    private Boolean onPrescription;

    @Column
    private String additionalNote;

    @Column
    private float loyaltyPoints;

    @Column
    private int quantity;

    @Column
    private float price;
}
