package ftn.isa.sistemapoteka.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Supplier")
public class Supplier extends RegisteredUser {
}
