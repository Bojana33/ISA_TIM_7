package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.repository.DrugRepository;
import ftn.isa.sistemapoteka.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DrugServiceImpl implements DrugService {

    private DrugRepository drugRepository;

    @Autowired
    public DrugServiceImpl(DrugRepository drugRepository){
        this.drugRepository = drugRepository;
    }

    @Override
    public Collection<Drug> findAllDrugs() {
        return drugRepository.findAll();
    }

    @Override
    public Drug saveDrug(Drug drug) {
        Drug d = new Drug();
        d.setName(drug.getName());
        d.setCode(drug.getCode());
        d.setDrugType(drug.getDrugType());
        d.setContraindications(drug.getContraindications());
        d.setStructure(drug.getStructure());
        d.setDailyIntake(drug.getDailyIntake());
        d.setDrugShape(drug.getDrugShape());
        d.setAdditionalNote(drug.getAdditionalNote());
        d.setLoyaltyPoints(drug.getLoyaltyPoints());
        d.setProducer(drug.getProducer());
        d.setOnPrescription(drug.getOnPrescription());
        d.setQuantity(drug.getQuantity());
        this.drugRepository.save(d);
        return d;
    }

    @Override
    public Drug findByCode(Long code) {
        return this.drugRepository.findByCode(code);
    }
}
