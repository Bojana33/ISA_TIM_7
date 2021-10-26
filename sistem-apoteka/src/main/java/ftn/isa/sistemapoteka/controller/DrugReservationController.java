package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.DrugReservation;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.service.DrugReservationService;
import ftn.isa.sistemapoteka.service.impl.DrugServiceImpl;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

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
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView makeReservation(@PathVariable Long code, Model model) throws Exception {

        Drug drug = this.drugService.findByCode(code);
        DrugReservation dr = new DrugReservation();

        model.addAttribute("reservation", dr);
        model.addAttribute("patient", this.userService.getPatientFromPrincipal());
        model.addAttribute("drug", drug);
        model.addAttribute("pharmacies", this.pharmacyService.findAllThatContainsDrug(drug));

        return new ModelAndView("views/drugReservation");
    }

    @PostMapping("/{code}/makeReservation/submit")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView makeReservation(@ModelAttribute("reservation") @Valid DrugReservation drugReservation,
                                        @ModelAttribute("pickUpDate") String pickUpDate,
                                        @ModelAttribute("pharmacyDrop") String pharmacyDrop,
                                        @PathVariable("code") Long code, @RequestParam("patId") Long patId,
                                        @RequestParam("drugId") Long drugId, Model model) throws Exception  {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate ldt = LocalDate.parse(pickUpDate, formatter);

        BigInteger big = new BigInteger(32, new Random());

        Pharmacy ph = this.pharmacyService.findByName(pharmacyDrop);
        Patient pat = this.userService.getPatientFromPrincipal();

        DrugReservation dr = drugReservation;
        dr.setReservationNumber(big);
        dr.setPatient(pat);
        dr.setPharmacy(ph);
        dr.setDrug(this.drugService.findById(drugId));
        dr.setDateOfReservation(LocalDate.now());
        dr.setTakingDrugDate(ldt);

        boolean exist = this.reservationService.reservationAlreadyExists(dr);
        if (exist) {
            model.addAttribute("patient", pat);
            return new ModelAndView("views/alreadyExist");
        }
        this.drugService.decrementQuantity(drugId);
        this.reservationService.saveDR(dr);

        this.reservationService.sendEmail(dr);

        return new ModelAndView("redirect:/drugs/allDrugs");

    }

    @GetMapping("/")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView showReservations(Model model) throws Exception {
        model.addAttribute("patient", this.userService.getPatientFromPrincipal());
        model.addAttribute("reservations", reservationService.findAllByDeleted(false));

        return new ModelAndView("views/reservations");
    }

    @GetMapping("/cancel/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView cancelReservation(@PathVariable Long id, Model model) throws Exception {
        DrugReservation reservation = this.reservationService.findById(id);
        Patient patient = this.userService.getPatientFromPrincipal();
        model.addAttribute("patient", patient);

        boolean cancel = this.reservationService.canBeCanceled(reservation);
        if (!cancel) {
            model.addAttribute("appointment", true);
            return new ModelAndView("views/reservationCancelError");
        }

        reservation.setDeleted(true);
        //this.reservationService.deleteById(reservation.getId());
        this.drugService.incrementQuantity(id);

        return new ModelAndView("redirect:/drugReservations/");
    }

}
