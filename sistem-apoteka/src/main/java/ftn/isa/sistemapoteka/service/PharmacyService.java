package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.fieldMatch.FieldMatch;
import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.model.Pharmacy;

import java.util.List;
import java.util.Map;

public interface PharmacyService {

    Pharmacy save(Pharmacy pharmacy) throws Exception;

    Pharmacy findById(Long id);

    List<Pharmacy> findAll();

    Map<Pharmacy,Double> findByDrug(Drug drug);

}
