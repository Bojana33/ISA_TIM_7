package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.exception.ResourceConflictException;
import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.service.DrugService;
import ftn.isa.sistemapoteka.service.impl.DrugServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/drugs")
public class DrugController {

    private DrugServiceImpl drugService;
    private UserServiceImpl userService;

    @Autowired
    public DrugController(DrugServiceImpl drugService, UserServiceImpl userService){
        this.drugService = drugService;
        this.userService = userService;
    }

    @GetMapping("/allDrugs")
    public ModelAndView getAllDrugs(Model model, String keyword) {
        if (keyword != null) {
            model.addAttribute("drugs", this.drugService.findByName(keyword));
        } else
        {
            model.addAttribute("drugs", this.drugService.findAllDrugs());
        }
        return new ModelAndView("views/drugs");
    }

    @GetMapping("/allDrugs/page/{pageNum}")
    public ModelAndView showDrugPages(@PathVariable int pageNum, Model model, String keyword) {
        int pageSize = 5;

        Page<Drug> page = this.drugService.findPaginated(pageNum, pageSize);
        List<Drug> drugs = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("drugs", drugs);
        if (keyword != null) {
            model.addAttribute("drugs", this.drugService.findByName(keyword));
        } else
        {
            model.addAttribute("drugs", this.drugService.findAllDrugs());
        }

        return new ModelAndView("views/paginatedDrugs");
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