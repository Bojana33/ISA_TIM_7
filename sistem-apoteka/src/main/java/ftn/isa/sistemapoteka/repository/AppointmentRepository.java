package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    @Query(value = "SELECT * from Appointment app WHERE app.scheduled = true", nativeQuery = true)
    List<Appointment> findScheduled();

    @Query(value = "SELECT * from Appointment app WHERE app.pharmacy_id = ?1", nativeQuery = true)
    List<Appointment> findAllByPharmacy(@Param("phId")Long id);
}
