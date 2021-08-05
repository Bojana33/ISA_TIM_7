package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends JpaRepository<Drug,Long> {
}
