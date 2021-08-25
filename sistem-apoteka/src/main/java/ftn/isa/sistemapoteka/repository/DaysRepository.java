package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Days;
import ftn.isa.sistemapoteka.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaysRepository extends JpaRepository<Days, Long> {
    List<Days> findDaysByVacationReviewedIsNullAndPharmacist_Pharmacy(Pharmacy pharmacy);

    List<Days> findDaysByVacationReviewedIsNullAndDermatologist_Pharmacies(Pharmacy pharmacy);
}
