package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("dermatologist")
public class Dermatologist extends RegisteredUser{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}
