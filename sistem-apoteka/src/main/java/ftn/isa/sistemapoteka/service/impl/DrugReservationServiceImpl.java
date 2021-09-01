package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.DrugReservation;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.repository.DrugRepository;
import ftn.isa.sistemapoteka.repository.DrugReservationRepository;
import ftn.isa.sistemapoteka.service.DrugReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class DrugReservationServiceImpl implements DrugReservationService {

    private DrugReservationRepository drugReservationRepository;
    private DrugRepository drugRepository;

    @Autowired
    public DrugReservationServiceImpl(DrugReservationRepository drugReservationRepository,
                                      DrugRepository drugRepository) {
        this.drugReservationRepository = drugReservationRepository;
        this.drugRepository = drugRepository;
    }

    @Override
    public DrugReservation findById(Long id) throws Exception {
        if (!this.drugReservationRepository.findById(id).isPresent()) {
            throw new Exception("No such value(Drug reservation service)");
        }

        return this.drugReservationRepository.findById(id).get();
    }

    @Override
    public DrugReservation save(DrugReservation drugReservation) throws Exception {
        if (this.drugReservationRepository.findById(drugReservation.getId()).isPresent()) {
            throw new Exception("Value already exist(Drug reservation service-save)");
        }

        return this.drugReservationRepository.save(drugReservation);
    }

    public void makeReservationHardcodeDate(DrugReservation drugReservation, Patient patient,
                                Long drugCode) throws Exception {
        Drug drug = this.drugRepository.findByCode(drugCode);

        DrugReservation dr = findById(drugReservation.getId());
        dr.setPatient(patient);
        dr.setDrug(drug);

        LocalDate date = LocalDate.now();
        dr.setDateOfReservation(date);
        dr.setTakingDrugDate(date.plusDays(1));
    }

    @Override
    public DrugReservation saveDR(DrugReservation dr) {
        return this.drugReservationRepository.save(dr);
    }
}
