package ftn.isa.sistemapoteka.Repository;

import ftn.isa.sistemapoteka.Entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends JpaRepository<Drug,Long> {
}
