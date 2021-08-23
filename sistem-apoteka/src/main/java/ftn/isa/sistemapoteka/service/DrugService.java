package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.DrugPricePeriod;

import java.util.Collection;
import java.util.List;

public interface DrugService {
    Collection<Drug> findAllDrugs();
    Drug saveDrug(Drug drug);
    Drug findByCode(Long code);

    void deleteDrug(Long id);

    boolean addDrugPrice(DrugPricePeriod drugPricePeriod);

    void removeFromPharmacy(Long drugId, Long pharmacyId);
    List<Drug> findInPharmacy(String drugName, Long pharmacyId);
    boolean addToPharmacy(Drug drug, Long pharmacyId);
    Drug editDrug(Drug newDrug, Long drugId, Long pharmacyId);
}
