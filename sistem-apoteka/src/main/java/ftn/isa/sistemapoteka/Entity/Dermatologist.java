package ftn.isa.sistemapoteka.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("dermatologist")
@Table(name = "dermatologists")
public class Dermatologist extends RegisteredUser{
}
