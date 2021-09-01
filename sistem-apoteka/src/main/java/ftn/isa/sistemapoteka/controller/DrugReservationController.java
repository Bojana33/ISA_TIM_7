package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.DrugReservation;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.service.DrugReservationService;
import ftn.isa.sistemapoteka.service.impl.DrugServiceImpl;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("drugReservations")
public class DrugReservationController {

    private DrugReservationService reservationService;
    private DrugServiceImpl drugService;
    private PharmacyServiceImpl pharmacyService;
    private UserServiceImpl userService;

    @Autowired
    public DrugReservationController(DrugReservationService reservationService, DrugServiceImpl drugService,
                                     PharmacyServiceImpl pharmacyService, UserServiceImpl userService) {
        this.reservationService = reservationService;
        this.drugService = drugService;
        this.pharmacyService = pharmacyService;
        this.userService = userService;
    }

    @GetMapping("/{code}/makeReservation")
    public ModelAndView makeReservation(@PathVariable Long code, Model model) throws Exception {

        Drug drug = this.drugService.findByCode(code);
        DrugReservation dr = new DrugReservation();
        dr.setId(1L);
        //this.reservationService.saveDR(dr);

        model.addAttribute("reservation", dr);
        model.addAttribute("patient", this.userService.getPatientFromPrincipal());
        model.addAttribute("drug", drug);
        model.addAttribute("pharmacies", this.pharmacyService.findAllThatContainsDrug(drug));

        return new ModelAndView("views/drugReservation");
    }

    @PostMapping("/{code}/makeReservation/submit")
    public ModelAndView makeReservation(@ModelAttribute("reservation") @Valid DrugReservation drugReservation,
                                        @ModelAttribute("patient") @Valid Patient patient,
                                        @ModelAttribute("pickUpDate") String pickUpDate,
                                        @PathVariable Long code, @RequestParam("patId") Long patId) throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate ldt = LocalDate.parse(pickUpDate, formatter);

        Patient pat = this.userService.findPatientById(patId);

        DrugReservation dr = drugReservation;
        dr.setPatient(pat);
        dr.setDrug(this.drugService.findByCode(code));
        dr.setDateOfReservation(LocalDate.now());
        dr.setTakingDrugDate(ldt);
        this.reservationService.saveDR(dr);


        return new ModelAndView("redirect:/drugs/allDrugs/");

    }
}
