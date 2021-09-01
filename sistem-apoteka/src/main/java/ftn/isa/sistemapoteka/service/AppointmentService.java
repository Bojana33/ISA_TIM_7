package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Appointment;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.model.Pharmacy;

import java.security.Principal;
import java.util.List;

public interface AppointmentService {

    Appointment findById(Long id) throws Exception;
    List<Appointment> findAll();
    Appointment save(Appointment appointment) throws Exception;
    Appointment update(Appointment appointment) throws Exception;

    void makeAppointment(Appointment appointment, Pharmacy pharmacy,
                                Long patientId) throws Exception;

    List<Appointment> findScheduledByPatient(Long phId);
    //List<Appointment> findAllScheduled();
    List<Appointment> findAllByPharmacy(Long phId);
    List<Appointment> findAllByPharmacyAndAdvising(Long phId, Boolean advising);

    void cancelAppointment(Appointment appointment, Pharmacy pharmacy,
                           Long patientId) throws Exception;
}
