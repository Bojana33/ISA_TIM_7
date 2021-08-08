package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Pharmacy;

public interface PharmacyService {

    Pharmacy save(Pharmacy pharmacy);

    Pharmacy findById(Long id);
}
