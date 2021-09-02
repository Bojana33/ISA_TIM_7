package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.DrugPricePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugPricePeriodRepository extends JpaRepository<DrugPricePeriod, Long> {

}
