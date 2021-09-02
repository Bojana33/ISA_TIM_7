package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.PharmacyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyReportRepository extends JpaRepository<PharmacyReport, Long> {

}
