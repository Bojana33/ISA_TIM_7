package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug,Long> {
    Drug findByCode(Long code);
    void deleteById(Long id);
    Drug getById(Long id);

    List<Drug> findAllByName (String name);

}
