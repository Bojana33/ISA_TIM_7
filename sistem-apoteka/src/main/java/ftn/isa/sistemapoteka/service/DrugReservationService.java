package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.DrugReservation;
import ftn.isa.sistemapoteka.model.Patient;

public interface DrugReservationService {
    DrugReservation findById(Long id) throws Exception;

    DrugReservation save(DrugReservation drugReservation) throws Exception;

    void makeReservationHardcodeDate(DrugReservation drugReservation, Patient patient,
                                     Long drugCode) throws Exception;

    DrugReservation saveDR(DrugReservation dr);
}
