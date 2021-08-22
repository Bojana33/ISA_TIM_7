package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class OrderForm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @NotEmpty(message = "You must choose at least one drug")
    private Set<Drug> drugs = new HashSet<>();

    @Column
    @NotEmpty(message = "You must fill this field")
    private int quantity;

    @Column
    @NotEmpty(message = "You must fill this field")
    private LocalDateTime offerDueDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Offer> offers = new HashSet<>();

    @ManyToOne(targetEntity = Pharmacy.class)
    private Pharmacy pharmacy;

    @ManyToOne(targetEntity = Pharmacy.class)
    private PharmacyAdministrator creator;

    public OrderForm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
