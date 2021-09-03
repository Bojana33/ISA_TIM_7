package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.fieldMatch.FieldMatch;
import ftn.isa.sistemapoteka.model.*;

import java.util.List;
import java.util.Map;

public interface PharmacyService {

    Pharmacy save(Pharmacy pharmacy) throws Exception;

    Pharmacy findById(Long id);

    List<Pharmacy> findAll();

    Map<Pharmacy,Double> findByDrug(Drug drug);

    List<Pharmacy> findPharmaciesByDrugReservationsConsultationsAppointments(List<DrugReservation> drugReservations, List<Consultation> consultations, List<Appointment> appointments);

    List<Pharmacy> findByDrugReservationOrConsultationsOrAppointments(DrugReservation drugReservation, Consultation consultation, Appointment appointment);

    List<Pharmacy> findByAppointments(Appointment appointment);

    List<Pharmacy> findByConsultations(Consultation consultation);

    List<Pharmacy> findByDrugReservations(DrugReservation drugReservation);

    List<Pharmacy> findByDrugReservationsOrConsultations(DrugReservation drugReservation,Consultation consultation);

    List<Pharmacy> findByDrugReservationsOrAppointments(DrugReservation drugReservation,Appointment appointment);

    List<Pharmacy> findByConsultationsOrAppointments(Consultation consultation,Appointment appointment);

    List<Pharmacy> findByPatientDrugs(List<Long> drugs);

    Pharmacy update(Pharmacy pharmacy);

    List<Pharmacy> sortByNameAsc();

    List<Pharmacy> sortByAddressAsc();

    List<Pharmacy> sortByAverageRatingAsc();

}
