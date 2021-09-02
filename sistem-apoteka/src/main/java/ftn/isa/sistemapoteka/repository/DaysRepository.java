package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Days;
import ftn.isa.sistemapoteka.model.Dermatologist;
import ftn.isa.sistemapoteka.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DaysRepository extends JpaRepository<Days, Long> {
    List<Days> findDaysByVacationReviewedIsNullAndPharmacist_Pharmacy(Pharmacy pharmacy);

    List<Days> findDaysByDermatologistAndAppointedIsFalseAndVacationSubmittedIsFalse(Dermatologist dermatologist);

    List<Days> findDaysByVacationReviewedIsNullAndDermatologist(Set<Dermatologist> dermatologists);
}
