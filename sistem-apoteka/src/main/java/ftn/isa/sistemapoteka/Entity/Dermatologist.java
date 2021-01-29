package ftn.isa.sistemapoteka.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("dermatologist")
public class Dermatologist extends RegisteredUser{
}
