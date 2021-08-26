package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByEmail(String email);

    Optional<Patient> findById(Long id);
    @Transactional
    @Modifying
    @Query("UPDATE Patient u " +
            "SET u.enabled = TRUE WHERE u.email = ?1")
    int enableUser(String email);

    Patient findByEmailAndPassword(String email, String password) throws Exception;
}
