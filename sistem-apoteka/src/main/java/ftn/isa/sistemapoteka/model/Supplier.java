package ftn.isa.sistemapoteka.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Supplier")
public class Supplier extends User {

    @ManyToMany
    //@JoinTable(name = "Ord")
    private Set<OrderForm> orderForms = new HashSet<>();
}
