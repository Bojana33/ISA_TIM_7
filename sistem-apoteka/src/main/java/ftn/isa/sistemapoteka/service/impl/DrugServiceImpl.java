package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.repository.DrugRepository;
import ftn.isa.sistemapoteka.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
        this.drugRepository.save(drug);
        return drug;
    }

    @Override
    public Drug findByCode(Long code) {
        return this.drugRepository.findByCode(code);
    }

    @Override
    public List<Drug> findByName(String name) {
        return this.drugRepository.findByKeyword(name);
    }
}
