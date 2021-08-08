package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyProgram implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double consultationPoints;

    @Column
    private Double appointmentPoints;

    @Column
    private Double regularPoints;

    @Column
    private Double silverPoints;

    @Column
    private Double goldPoints;

    @Column
    private Integer discountRegular;

    @Column
    private Integer discountSilver;

    @Column
    private Integer discountGold;

}
