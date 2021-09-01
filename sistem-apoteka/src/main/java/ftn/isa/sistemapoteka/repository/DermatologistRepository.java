package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Appointment;
import ftn.isa.sistemapoteka.model.Dermatologist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DermatologistRepository extends JpaRepository<Dermatologist, Long> {

    Dermatologist findByAppointments(Appointment appointment);
}
