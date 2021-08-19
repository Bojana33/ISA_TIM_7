package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.repository.PharmacyRepository;
import ftn.isa.sistemapoteka.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
