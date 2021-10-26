package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Supplier")
@AllArgsConstructor
@NoArgsConstructor
public class Supplier extends User {

    @ManyToMany
    //@JoinTable(name = "Ord")
    private Set<OrderForm> orderForms = new HashSet<>();

    public Supplier(String email, String password, String firstName, String lastName, String city, String residence, String state, String phoneNumber, Boolean enabled, Collection<? extends GrantedAuthority> authorities) {
    }
}
