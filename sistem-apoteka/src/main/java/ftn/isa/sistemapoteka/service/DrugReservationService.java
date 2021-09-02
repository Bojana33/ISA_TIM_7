package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.DrugReservation;
import ftn.isa.sistemapoteka.model.Patient;

import java.util.List;

public interface DrugReservationService {
    DrugReservation findById(Long id) throws Exception;
    List<DrugReservation> findAll();

    DrugReservation save(DrugReservation drugReservation) throws Exception;
    void deleteById(Long id);

    /*void makeReservationHardcodeDate(DrugReservation drugReservation, Patient patient,
                                     Long drugCode) throws Exception;*/

    DrugReservation saveDR(DrugReservation dr);

    Boolean reservationAlreadyExists(DrugReservation drugReservation) throws Exception;

    void sendEmail(DrugReservation dr) throws Exception;

    boolean canBeCanceled(DrugReservation reservation);

    List<DrugReservation> findAllByDeleted(boolean deleted);
}
