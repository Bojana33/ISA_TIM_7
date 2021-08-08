package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Drug;

import java.util.Collection;

public interface DrugService {
    Collection<Drug> findAllDrugs();
    Drug saveDrug(Drug drug);
    Drug findByCode(Long code);
}
