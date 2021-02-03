package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("system_administrator")
public class SystemAdministrator extends RegisteredUser{

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LoyaltyProgram> loyaltyPrograms = new HashSet<>();

    public SystemAdministrator() {
        super();
    }

    public SystemAdministrator(@NotEmpty(message = "This field can not be empty") String name, @NotEmpty(message = "This field can not be empty") String lastname, @NotEmpty(message = "This field can not be empty") String email, @NotEmpty(message = "This field can not be empty") String password, String residence, String city, String state, String phoneNumber) {
        super(name, lastname, email, password, residence, city, state, phoneNumber);
    }

    public Set<LoyaltyProgram> getLoyaltyPrograms() {
        return loyaltyPrograms;
    }

    public void setLoyaltyPrograms(Set<LoyaltyProgram> loyaltyPrograms) {
        this.loyaltyPrograms = loyaltyPrograms;
    }
}
