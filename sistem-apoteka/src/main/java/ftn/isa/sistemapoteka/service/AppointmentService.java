package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Appointment;
import ftn.isa.sistemapoteka.model.Consultation;
import ftn.isa.sistemapoteka.model.Patient;

import java.util.List;

public interface AppointmentService {

    List<Appointment> findAllByPatient(Patient patient);
}
