package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/pharmacies")
public class PharmacyController {

    private final PharmacyServiceImpl pharmacyServiceImpl;
    private final UserServiceImpl userService;

    @Autowired
    public PharmacyController(PharmacyServiceImpl pharmacyServiceImpl, UserServiceImpl userService) {
        this.pharmacyServiceImpl = pharmacyServiceImpl;
        this.userService = userService;
    }

    @GetMapping(value = "/registerPharmacy")
    public ModelAndView registerPharmacyForm(Model model) {
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

}
