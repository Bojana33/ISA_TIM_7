package ftn.isa.sistemapoteka.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Patient extends RegisteredUser{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id")
    private Long id;

    @Column
    private Float loyaltyPoints;

    @OneToMany(targetEntity = Drug.class)
    private Set<Drug> allergies = new HashSet<>();

    @OneToMany(targetEntity = Appointment.class)
    private Set<Appointment> appointments = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

}
