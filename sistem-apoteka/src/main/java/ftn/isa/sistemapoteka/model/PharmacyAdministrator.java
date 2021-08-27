package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("pharmacy_administrator")
public class PharmacyAdministrator extends User {

    @ManyToOne(targetEntity = Pharmacy.class)
    private Pharmacy pharmacy;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private Set<OrderForm> created = new HashSet<>();
}
