package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug,Long> {
    Drug findByCode(Long code);

    List<Drug> findByNameContaining(String name);

    @Query(value="SELECT * FROM Drug d where lower(d.name) like lower(concat('%', ?1, '%')) or lower(d.producer) like lower(concat('%', ?1, '%')) ",
            nativeQuery = true)
    List<Drug> findByKeyword(@Param("keyword") String keyword);

}
