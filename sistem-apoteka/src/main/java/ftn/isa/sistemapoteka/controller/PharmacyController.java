package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.exception.ResourceConflictException;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.model.PharmacyAdministrator;
import ftn.isa.sistemapoteka.model.User;
import ftn.isa.sistemapoteka.service.PharmacyService;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/pharmacies")
public class PharmacyController {

    private PharmacyServiceImpl pharmacyServiceImpl;
    private UserServiceImpl userService;

    @Autowired
    public PharmacyController(PharmacyServiceImpl pharmacyServiceImpl, UserServiceImpl userService){
        this.pharmacyServiceImpl = pharmacyServiceImpl;
        this.userService = userService;
    }

    @GetMapping(value = "/registerPharmacy")
    public ModelAndView registerPharmacyForm(Model model){
        Pharmacy pharmacy = new Pharmacy();
        model.addAttribute(pharmacy);
        return new ModelAndView("registerPharmacy");
    }

    @PostMapping("/registerPharmacy/submit")
    public ModelAndView registerPharmacySubmit(@ModelAttribute Pharmacy pharmacy) throws Exception{
        this.pharmacyServiceImpl.save(pharmacy);
        return new ModelAndView("redirect:/user/registerPharmacyAdmin/" + pharmacy.getId());
    }

    @GetMapping("/allPharmacies")
    public ModelAndView allPharmacies(Model model){
        model.addAttribute("pharmacies",this.pharmacyServiceImpl.findAll());
        return new ModelAndView("pharmacies");
    }

    @GetMapping("/pharmacy/{id}")
    public ModelAndView pharmacy(Model model, @PathVariable Long id){
        Pharmacy pharmacy = this.pharmacyServiceImpl.findById(id);
        model.addAttribute(pharmacy);
        return new ModelAndView("pharmacy");
    }

    @RequestMapping("/subscribe/{id}")
    public ModelAndView subsribePatient(Model model, @PathVariable Long id, Authentication authentication) throws Exception{
        Patient patient = (Patient) this.userService.findByEmail(authentication.getName());
        Pharmacy pharmacy = this.pharmacyServiceImpl.findById(id);
        this.userService.subscribePatient(patient,pharmacy);
        model.addAttribute(pharmacy);
        return new ModelAndView("redirect:/pharmacies/pharmacy/"+id);
    }

    @RequestMapping("/unsubscribe/{id}")
    public ModelAndView unsubsribePatient(Model model, @PathVariable Long id, Authentication authentication){
        Patient patient = (Patient) this.userService.findByEmail(authentication.getName());
        Pharmacy pharmacy = this.pharmacyServiceImpl.findById(id);
        this.userService.unsubscribePatient(patient,pharmacy);
        Set<Pharmacy> pharmacies = this.userService.findAllSubscribedPharmacies(patient);
        model.addAttribute("pharmacies",pharmacies);
        return new ModelAndView("redirect:/user/subscribedPharmacies");
    }

}
