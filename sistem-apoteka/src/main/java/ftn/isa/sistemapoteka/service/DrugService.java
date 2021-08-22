package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Drug;

import java.util.Collection;
import java.util.List;

public interface DrugService {
    Collection<Drug> findAllDrugs();
    Drug saveDrug(Drug drug);
    Drug findByCode(Long code);

    List<Drug> findByName(String name);
}
