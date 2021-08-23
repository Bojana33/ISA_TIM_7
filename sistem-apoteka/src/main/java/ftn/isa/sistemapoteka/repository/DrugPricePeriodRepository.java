package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.DrugPricePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DrugPricePeriodRepository extends JpaRepository<DrugPricePeriod, Long> {
    Set<DrugPricePeriod> getAll();
}
