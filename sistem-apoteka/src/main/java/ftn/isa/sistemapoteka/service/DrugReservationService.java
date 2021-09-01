package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.fieldMatch.FieldMatch;
import ftn.isa.sistemapoteka.model.DrugReservation;
import ftn.isa.sistemapoteka.model.Patient;
import jdk.dynalink.linker.LinkerServices;

import java.util.List;

public interface DrugReservationService {

    List<DrugReservation> findAllByPatient(Patient patient);
}
