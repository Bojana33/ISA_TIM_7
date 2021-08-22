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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = "/drugs")
public class DrugController {

    private DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService){
        this.drugService = drugService;
    }

    @GetMapping("/allDrugs")
    public ModelAndView getAllDrugs(Model model, String keyword) {
        if (keyword != null) {
            model.addAttribute("drugs", this.drugService.findByName(keyword));
        } else
        {
            model.addAttribute("drugs", this.drugService.findAllDrugs());
        }
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