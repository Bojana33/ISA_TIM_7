package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.repository.PharmacyRepository;
import ftn.isa.sistemapoteka.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private PharmacyRepository pharmacyRepository;

    @Autowired
    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository){
        this.pharmacyRepository = pharmacyRepository;
    }

    @Override
    public Pharmacy save(Pharmacy pharmacy) throws Exception {
        if (this.pharmacyRepository.findByName(pharmacy.getName()) != null){
            throw new Exception("Pharmacy with that name already exist!");
        }
        return this.pharmacyRepository.save(pharmacy);
    }

    @Override
    public Pharmacy findById(Long id) {
        return this.pharmacyRepository.getById(id);
    }

    @Override
    public List<Pharmacy> findAll() {
        return this.pharmacyRepository.findAll();
    }

    @Override
    public Map<Pharmacy,Double> findByDrug(Drug drug) {
        List<Pharmacy> pharmacies = findAll();
        Map<Pharmacy,Double> showList = new HashMap<>();
        for (Pharmacy pharmacy : pharmacies){
            if (pharmacy.getDrugsQuantity().containsKey(drug.getCode()) && pharmacy.getDrugsQuantity().get(drug.getCode()) > 0){
                showList.put(pharmacy,pharmacy.getDrugs().get(drug.getCode()));
            }
        }
        return showList;
    }


}
