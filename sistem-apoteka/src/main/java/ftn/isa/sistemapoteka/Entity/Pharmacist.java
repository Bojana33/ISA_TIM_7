package ftn.isa.sistemapoteka.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pharmacist {

    @Id
    @GeneratedValue
    private Long id;
}
