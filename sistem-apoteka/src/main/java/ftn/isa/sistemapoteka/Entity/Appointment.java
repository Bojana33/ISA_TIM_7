package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private Float loyaltyPoints;

    @Column
    private Float price;

    @Column
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "dermatologist_id", nullable = false)
    private Dermatologist dermatologist;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Float loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
