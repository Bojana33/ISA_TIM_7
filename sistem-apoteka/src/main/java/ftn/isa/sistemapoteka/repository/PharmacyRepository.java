package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    Pharmacy getById(Long id);
    Pharmacy findByName(String name);

    Set<Pharmacy> findAllBySubscriptionedPatients(Patient patient);
}
