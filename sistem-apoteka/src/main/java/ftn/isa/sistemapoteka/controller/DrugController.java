package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.authFacade.AuthenticationFacade;
import ftn.isa.sistemapoteka.exception.ResourceConflictException;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.service.DrugService;
import ftn.isa.sistemapoteka.service.impl.DrugReservationServiceImpl;
import ftn.isa.sistemapoteka.service.impl.DrugServiceImpl;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/drugs")
public class DrugController {

    private DrugServiceImpl drugService;
    private UserServiceImpl userService;
    private DrugReservationServiceImpl reservationService;
    private PharmacyServiceImpl pharmacyService;

    @Autowired
    public DrugController(DrugServiceImpl drugService, UserServiceImpl userService,
                          PharmacyServiceImpl pharmacyService, DrugReservationServiceImpl reservationService) {
        this.drugService = drugService;
        this.userService = userService;
        this.reservationService = reservationService;
        this.pharmacyService = pharmacyService;
    }

    @GetMapping("/allDrugs")
    public ModelAndView getAllDrugs(Model model, String keyword) throws Exception {
        if (keyword != null) {
            model.addAttribute("drugs", this.drugService.findByName(keyword));
        } else {
            model.addAttribute("drugs", this.drugService.findAllDrugs());
        }
        try {
            model.addAttribute("principal", this.userService.getPatientFromPrincipal());
            return new ModelAndView("views/drugs");
        } catch(Exception e) {
            return new ModelAndView("views/drugsGuests");
        }
        //model.addAttribute("principal", this.userService.getPatientFromPrincipal());
        //return new ModelAndView("views/drugs");
    }

    @GetMapping("/")
    ModelAndView getAllDrugs(Model model) {
        model.addAttribute("drugs", this.drugService.findAllDrugs());
        return new ModelAndView("drugs");
    }

    @GetMapping("/addDrug")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView addDrugForm(Model model) {
        Drug drug = new Drug();
        model.addAttribute("drug", drug);
        return new ModelAndView("addDrugForm");
    }

    @PostMapping(value = "/addDrug/submit")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView addDrug(@ModelAttribute Drug drug) throws Exception {
        if (this.drugService.findByCode(drug.getCode()) != null) {
            throw new ResourceConflictException(drug.getCode(), "Drug with this code already exist");
        }
        this.drugService.saveDrug(drug);
        return new ModelAndView("redirect:/drugs/");
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
        } else {
            model.addAttribute("drugs", this.drugService.findAllDrugs());
        }

        return new ModelAndView("views/paginatedDrugs");
    }




}