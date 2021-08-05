package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.DrugReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugReservationRepository extends JpaRepository<DrugReservation,Long> {
}
