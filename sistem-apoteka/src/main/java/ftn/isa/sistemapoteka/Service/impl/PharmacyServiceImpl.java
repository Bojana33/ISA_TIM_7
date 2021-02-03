package ftn.isa.sistemapoteka.Service.impl;

import ftn.isa.sistemapoteka.Repository.PharmacyRepository;
import ftn.isa.sistemapoteka.Service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private PharmacyRepository pharmacyRepository;

    @Autowired
    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository){
        this.pharmacyRepository = pharmacyRepository;
    }
}
