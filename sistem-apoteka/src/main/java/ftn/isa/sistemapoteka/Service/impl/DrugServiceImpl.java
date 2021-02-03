package ftn.isa.sistemapoteka.Service.impl;

import ftn.isa.sistemapoteka.Repository.DrugRepository;
import ftn.isa.sistemapoteka.Service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugServiceImpl implements DrugService {

    private DrugRepository drugRepository;

    @Autowired
    public DrugServiceImpl(DrugRepository drugRepository){
        this.drugRepository = drugRepository;
    }
}
