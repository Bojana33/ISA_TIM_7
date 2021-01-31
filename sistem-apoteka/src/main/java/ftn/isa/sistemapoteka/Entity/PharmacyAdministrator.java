package ftn.isa.sistemapoteka.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PharmacyAdministrator")
public class PharmacyAdministrator extends RegisteredUser{
}
