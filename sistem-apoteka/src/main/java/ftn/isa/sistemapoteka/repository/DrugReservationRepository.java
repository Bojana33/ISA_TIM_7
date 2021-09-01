package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.DrugReservation;
import ftn.isa.sistemapoteka.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugReservationRepository extends JpaRepository<DrugReservation,Long> {

    List<DrugReservation> findAllByPatient(Patient patient);
}
