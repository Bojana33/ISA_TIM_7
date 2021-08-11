package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("drugs")
public class DrugController {

    private final DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService){
        this.drugService = drugService;
    }

    @GetMapping("/")
    Collection<Drug> getAllDrugs(){
        return drugService.findAllDrugs();
    }
}
