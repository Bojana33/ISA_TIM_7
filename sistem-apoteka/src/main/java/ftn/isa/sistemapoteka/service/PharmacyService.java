package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.fieldMatch.FieldMatch;
import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.Pharmacy;

import java.time.LocalDateTime;
import java.util.List;

public interface PharmacyService {

    Pharmacy save(Pharmacy pharmacy) throws Exception;
    Pharmacy updateAppointments(Pharmacy pharmacy) throws Exception;

    Pharmacy findById(Long id) throws Exception;
    List<Pharmacy> findAll();

    List<Pharmacy> findByKeyword(String keyword);
    List<Pharmacy> orderByNameAsc();
    List<Pharmacy> orderByNameDesc();
    List<Pharmacy> orderByRatingAsc();
    List<Pharmacy> orderByRatingDesc();

    List<Pharmacy> findAllThatContainsDrug(Drug drug);
    //List<Pharmacy> findAllWithAvailablePharmacists(LocalDateTime );
}
