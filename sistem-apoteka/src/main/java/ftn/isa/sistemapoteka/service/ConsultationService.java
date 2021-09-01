package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Consultation;
import ftn.isa.sistemapoteka.model.Patient;

import java.util.List;

public interface ConsultationService {

    List<Consultation> findAllByPatient(Patient patient);
}
