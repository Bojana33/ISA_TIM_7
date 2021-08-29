package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.authFacade.AuthenticationFacade;
import ftn.isa.sistemapoteka.exception.ResourceConflictException;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.service.DrugService;
import ftn.isa.sistemapoteka.service.impl.DrugReservationServiceImpl;
import ftn.isa.sistemapoteka.service.impl.DrugServiceImpl;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/drugs")
public class DrugController {

    private DrugServiceImpl drugService;
    private UserServiceImpl userService;
    private AuthenticationFacade facade;
    private DrugReservationServiceImpl reservationService;

    @Autowired
    public DrugController(DrugServiceImpl drugService, UserServiceImpl userService,
                          AuthenticationFacade facade, DrugReservationServiceImpl reservationService) {
        this.drugService = drugService;
        this.userService = userService;
        this.facade = facade;
        this.reservationService = reservationService;
    }

    @GetMapping("/allDrugs")
    public ModelAndView getAllDrugs(Model model, String keyword) {
        if (keyword != null) {
            model.addAttribute("drugs", this.drugService.findByName(keyword));
        } else {
            model.addAttribute("drugs", this.drugService.findAllDrugs());
        }
        return new ModelAndView("views/drugs");
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
    public ModelAndView addDrug(@ModelAttribute Drug drug) {
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

    @GetMapping("/allDrugs/{code}/makeReservation")
    public ModelAndView makeReservation(@PathVariable Long code, Model model) throws Exception {
        String email = this.facade.getAuthentication().getPrincipal().toString();

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        //formatter.format(date);

        DrugReservation dr = new DrugReservation();
        dr.setId(1L);
        this.reservationService.save(dr);
        model.addAttribute("reservation", dr);
        model.addAttribute("patient", this.userService.findPatientByEmail(email));
        model.addAttribute("drugCode", code);
        model.addAttribute("localDateTime", date);

        return new ModelAndView("views/drugReservation");
    }

    @PostMapping("/allDrugs/{code}/makeReservation")
    public ModelAndView makeReservation(@ModelAttribute("reservation") @Valid DrugReservation drugReservation,
                                        @ModelAttribute("patient") @Valid Patient patient,
                                        @ModelAttribute("localDateTime") Date ldt,
                                        BindingResult bindingResult,
                                        @PathVariable Long code) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        formatter.format(ldt);

        this.reservationService.makeReservationHardcodeDate(drugReservation, patient, code);

        //List<Pharmacy> pharmacyContainingDrug = this.drugService.findPharmaciesThatContainsDrug(drug);*/
        return new ModelAndView("redirect:/drugs/allDrugs/");

    }


    @GetMapping("/tst")
    public ModelAndView tst(Model model) throws Exception {
        String email = this.facade.getAuthentication().getPrincipal().toString();
        model.addAttribute("patient", this.userService.findPatientByEmail(email));

        return new ModelAndView("views/testSecurity");
    }

}