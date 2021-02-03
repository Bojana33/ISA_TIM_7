package ftn.isa.sistemapoteka.Service.impl;

import ftn.isa.sistemapoteka.Repository.DrugReservationRepository;
import ftn.isa.sistemapoteka.Service.DrugReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugReservationServiceImpl implements DrugReservationService {

    private DrugReservationRepository drugReservationRepository;

    @Autowired
    public DrugReservationServiceImpl(DrugReservationRepository drugReservationRepository){
        this.drugReservationRepository = drugReservationRepository;
    }
}
