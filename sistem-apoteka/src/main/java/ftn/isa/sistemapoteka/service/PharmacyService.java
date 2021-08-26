package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.fieldMatch.FieldMatch;
import ftn.isa.sistemapoteka.model.Pharmacy;

import java.util.List;

public interface PharmacyService {

    Pharmacy save(Pharmacy pharmacy) throws Exception;

    Pharmacy findById(Long id);

    List<Pharmacy> findAll();

    List<Pharmacy> findByKeyword(String keyword);
    List<Pharmacy> orderByNameAsc();
    List<Pharmacy> orderByNameDesc();
    List<Pharmacy> orderByRatingAsc();
    List<Pharmacy> orderByRatingDesc();
}
