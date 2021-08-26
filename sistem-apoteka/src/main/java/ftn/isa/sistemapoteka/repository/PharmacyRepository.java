package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    Pharmacy getById(Long id);
    Pharmacy findByName(String name);

    @Query(value="SELECT * FROM Pharmacy p where lower(p.name) like lower(concat('%', ?1, '%')) or lower(p.address) like lower(concat('%', ?1, '%')) ",
            nativeQuery = true)
    List<Pharmacy> findByKeyword(@Param("keyword") String keyword);

    List<Pharmacy> findByOrderByNameAsc();
    List<Pharmacy> findByOrderByNameDesc();
    List<Pharmacy> findByOrderByAverageRatingAsc();
    List<Pharmacy> findByOrderByAverageRatingDesc();
}
