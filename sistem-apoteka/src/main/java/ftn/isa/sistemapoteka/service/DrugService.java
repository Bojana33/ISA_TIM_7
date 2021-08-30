package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.DrugType;

import java.util.Collection;
import java.util.List;

public interface DrugService {
    Collection<Drug> findAllDrugs();
    Drug saveDrug(Drug drug);
    Drug findByCode(Long code);
    List<Drug> findDrugByName(String name);
    List<Drug> findDrugByType(DrugType drugType);
    List<Drug> filterDrugByRating(Double rating);
}
