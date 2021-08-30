package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.DrugType;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug,Long> {
    Drug findByCode(Long code);
    List<Drug> findAllByName(String name);
    List<Drug> findAllByDrugType(DrugType drugType);
    List<Drug> findAllByAverageRating(Double rating);
}
