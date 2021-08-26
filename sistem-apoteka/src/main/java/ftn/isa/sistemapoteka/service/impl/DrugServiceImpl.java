package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.repository.DrugRepository;
import ftn.isa.sistemapoteka.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Drug d = new Drug();
        d.setName(drug.getName());
        d.setCode(drug.getCode());
        d.setDrugType(drug.getDrugType());
        d.setContraindications(drug.getContraindications());
        d.setStructure(drug.getStructure());
        d.setDailyIntake(drug.getDailyIntake());
        this.drugRepository.save(d);
        return d;
    }

    @Override
    public Drug findByCode(Long code) {
        return this.drugRepository.findByCode(code);
    }

    @Override
    public List<Drug> findByName(String name) {
        return this.drugRepository.findByKeyword(name);
    }

    @Override
    public Page<Drug> findPaginated(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        return this.drugRepository.findAll(pageable);
    }
}
