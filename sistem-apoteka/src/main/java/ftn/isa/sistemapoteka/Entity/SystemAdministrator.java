package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SystemAdministrator extends RegisteredUser{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "SystemAdministrator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LoyaltyProgram> loyaltyPrograms = new HashSet<>();
}
