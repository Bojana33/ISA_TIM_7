package ftn.isa.sistemapoteka.Repository;

import ftn.isa.sistemapoteka.Entity.DrugReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugReservationRepository extends JpaRepository<DrugReservation,Long> {
}
