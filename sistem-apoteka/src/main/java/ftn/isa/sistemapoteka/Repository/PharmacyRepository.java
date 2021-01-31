package ftn.isa.sistemapoteka.Repository;

import ftn.isa.sistemapoteka.Entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
