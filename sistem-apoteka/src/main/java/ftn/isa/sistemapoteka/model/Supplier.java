package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@DiscriminatorValue("supplier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier extends User {

//    @ManyToMany
//    //@JoinTable(name = "Ord")
//    private Set<OrderForm> orderForms = new HashSet<>();

    @ElementCollection
    @MapKeyColumn(name = "drug_code")
    @Column(name = "quantity")
    private Map<Long, Integer> drugs = new HashMap<>();
}
