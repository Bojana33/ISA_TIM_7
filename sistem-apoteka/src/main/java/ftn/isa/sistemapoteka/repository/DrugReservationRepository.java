package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.DrugReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugReservationRepository extends JpaRepository<DrugReservation,Long> {
    @Override
    void deleteById(Long id);

    @Query(value = "SELECT * from drug_reservation dr where dr.deleted = ?1", nativeQuery = true)
    List<DrugReservation> findAllByDeleted(@Param("deleted") boolean deleted);
}
