package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.DrugReservation;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.repository.DrugReservationRepository;
import ftn.isa.sistemapoteka.service.DrugReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugReservationServiceImpl implements DrugReservationService {

    private DrugReservationRepository drugReservationRepository;

    @Autowired
    public DrugReservationServiceImpl(DrugReservationRepository drugReservationRepository){
        this.drugReservationRepository = drugReservationRepository;
    }


    @Override
    public List<DrugReservation> findAllByPatient(Patient patient) {
        return this.drugReservationRepository.findAllByPatient(patient);
    }
}
