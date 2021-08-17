package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Consultation;
import ftn.isa.sistemapoteka.model.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> getConsultationsByPharmacist(Pharmacist pharmacist);
}
