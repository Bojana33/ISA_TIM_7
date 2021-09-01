package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.repository.PharmacyRepository;
import ftn.isa.sistemapoteka.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    @Autowired
    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository){
        this.pharmacyRepository = pharmacyRepository;
    }

    @Override
    public Pharmacy save(Pharmacy pharmacy) throws Exception {
        return this.pharmacyRepository.save(pharmacy);
    }

    @Override
    public Pharmacy updateAppointments(Pharmacy pharmacy) throws Exception {
        if (!this.pharmacyRepository.findById(pharmacy.getId()).isPresent()) {
            throw new Exception("No value (Pharmacy service)");
        }
        Pharmacy forUpdate = this.pharmacyRepository.findById(pharmacy.getId()).get();

        forUpdate.setAppointments(pharmacy.getAppointments());
        this.pharmacyRepository.save(forUpdate);
        return forUpdate;
    }

    @Override
    public Pharmacy findById(Long id) throws Exception {
        if (this.pharmacyRepository.getById(id) == null) {
            throw new Exception("Pharmacy does not exist");
        }
        return this.pharmacyRepository.getById(id);
    }

    @Override
    public List<Pharmacy> findAll() {
        return this.pharmacyRepository.findAll();
    }

    @Override
    public List<Pharmacy> findByKeyword(String keyword) {
        return this.pharmacyRepository.findByKeyword(keyword);
    }

    @Override
    public List<Pharmacy> orderByNameAsc() { return this.pharmacyRepository.findByOrderByNameAsc(); }

    @Override
    public List<Pharmacy> orderByNameDesc() { return this.pharmacyRepository.findByOrderByNameDesc(); }

    @Override
    public List<Pharmacy> orderByRatingAsc() { return this.pharmacyRepository.findByOrderByAverageRatingAsc(); }

    @Override
    public List<Pharmacy> orderByRatingDesc() { return this.pharmacyRepository.findByOrderByAverageRatingDesc(); }

    @Override
    public List<Pharmacy> findAllThatContainsDrug(Drug drug) {
        List<Pharmacy> pharmacies = this.pharmacyRepository.findAll();
        List<Pharmacy> withDrug = new ArrayList<>();

        for (Pharmacy pharmacy : pharmacies) {
            for (Drug d : pharmacy.getDrugs()) {
                if (Objects.equals(d.getName(), drug.getName())) {
                    withDrug.add(pharmacy);
                }
            }
        }

        return withDrug;
    }
}
