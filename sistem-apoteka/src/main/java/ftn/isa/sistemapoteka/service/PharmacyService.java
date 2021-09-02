package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Pharmacist;
import ftn.isa.sistemapoteka.model.Pharmacy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PharmacyService {

    Pharmacy save(Pharmacy pharmacy) throws Exception;

    Pharmacy update(Pharmacy pharmacy);

    Pharmacy findById(Long id);

    List<Pharmacy> findAll();

    boolean removePharmacist(Pharmacy pharmacy, Pharmacist pharmacist);

    List<Pharmacist> findPharmacistByName(Pharmacy pharmacy, String name);

    Pharmacist addPharmacist(Pharmacist pharmacist, Pharmacy pharmacy, LocalDate beggining, LocalDate end, LocalTime dayBeggining, LocalTime dayEnd);


}
