package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {

}
