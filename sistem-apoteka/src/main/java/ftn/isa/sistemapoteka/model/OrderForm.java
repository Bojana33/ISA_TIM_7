package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class OrderForm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @MapKeyColumn(name = "drug_code")
    @Column(name = "quantity")
    private Map<Long,Integer> drugs = new HashMap<>();


    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm a")
    private LocalDateTime offerDueDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<Offer> offers = new HashSet<>();

    @ManyToOne(targetEntity = PharmacyAdministrator.class)
    private PharmacyAdministrator creator;

}
