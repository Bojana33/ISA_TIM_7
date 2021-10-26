package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("system_administrator")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SystemAdministrator extends User {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LoyaltyProgram> loyaltyPrograms = new HashSet<>();
}
