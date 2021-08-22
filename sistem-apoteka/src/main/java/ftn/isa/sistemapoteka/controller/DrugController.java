package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.exception.ResourceConflictException;
import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
@RequestMapping(value = "/drugs")
public class DrugController {

    private DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService){
        this.drugService = drugService;
    }

    @GetMapping("/")
    public ModelAndView getAllDrugs(Model model) {
        model.addAttribute("drugs", this.drugService.findAllDrugs());
        return new ModelAndView("drugs");
    }

    @PostMapping(value = "/addDrug")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ResponseEntity<Drug> addDrug(@RequestBody Drug drug){
        if (this.drugService.findByCode(drug.getCode()) != null){
            throw new ResourceConflictException(drug.getCode(),"Drug with this code already exist");
        }
        return new ResponseEntity<>(this.drugService.saveDrug(drug), HttpStatus.CREATED);
    }

}