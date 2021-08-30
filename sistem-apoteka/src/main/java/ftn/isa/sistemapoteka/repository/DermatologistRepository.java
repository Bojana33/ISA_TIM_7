package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Dermatologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DermatologistRepository extends JpaRepository<Dermatologist, Long> {
    Optional<Dermatologist> findById(Long id);
//    @Transactional
//    @Modifying
//    @Query("UPDATE Dermatologist u " +
//            "SET u.enabled = TRUE WHERE u.email = ?1")
//    int enableUser(String email);
}
