package ftn.isa.sistemapoteka.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("Pharmacist")
public class Pharmacist extends RegisteredUser{

}
