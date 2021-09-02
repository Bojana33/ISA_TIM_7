package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.Days;
import ftn.isa.sistemapoteka.model.Dermatologist;
import ftn.isa.sistemapoteka.model.Pharmacist;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.service.impl.DaysServiceImpl;
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
    private final DaysServiceImpl daysService;

    @Autowired
    public PharmacyController(PharmacyServiceImpl pharmacyServiceImpl, UserServiceImpl userService, DaysServiceImpl daysService) {
        this.pharmacyServiceImpl = pharmacyServiceImpl;
        this.userService = userService;
        this.daysService = daysService;
    }

    //@PreAuthorize("hasRole('PHARMACY_ADMIN')")
    @GetMapping(value = "/updatePharmacy/{pharmacistId}")
    public ModelAndView showPAdminsPharm(Model model, @PathVariable Long pharmacistId) {
        Pharmacist pharmacist = (Pharmacist) userService.findById(pharmacistId);
        model.addAttribute("pharmacy", pharmacist.getPharmacy());
        model.addAttribute("id", pharmacist.getPharmacy().getId());
        model.addAttribute("dermatologistFreeDays", daysService.getDermatologistFreeDays(pharmacist.getPharmacy()));
        return new ModelAndView("pharmacyEditable");
    }

    @GetMapping(value = "/pharmacy/home")
    public ModelAndView showPharm(Model model) {


        return new ModelAndView("pharmacyNonEditable");
    }

    //@PreAuthorize("hasRole('PHARMACY_ADMIN')")
    @PostMapping(value = "/updatePharmacy/{pharmacyId}")
    public ModelAndView updatePharmacy(Model model, @ModelAttribute Pharmacy pharmacy, @PathVariable Long pharmacyId) {
        this.pharmacyServiceImpl.update(pharmacy);
        model.addAttribute("pharmacy", pharmacy);

        return new ModelAndView("redirect:/pharmacies/pharmacy/home");
    }


    @GetMapping(value = "/registerPharmacy")
    public ModelAndView registerPharmacyForm(Model model) {
        Pharmacy pharmacy = new Pharmacy();
        model.addAttribute(pharmacy);
        return new ModelAndView("registerPharmacy");
    }

    @PostMapping("/registerPharmacy/submit")
    public ModelAndView registerPharmacySubmit(@ModelAttribute Pharmacy pharmacy) throws Exception {
        this.pharmacyServiceImpl.save(pharmacy);
        return new ModelAndView("redirect:/user/registerPharmacyAdmin/" + pharmacy.getId());
    }

    @GetMapping("/allPharmacies")
    public ModelAndView allPharmacies(Model model) {
        model.addAttribute("pharmacies", this.pharmacyServiceImpl.findAll());
        return new ModelAndView("pharmacies");
    }

    //@PreAuthorize("hasRole('PHARMACY_ADMIN')")
    @GetMapping("/addDermatologistWorkDays/{dermId}/")
    public ModelAndView defineDermaWorkDays(Model model, @ModelAttribute Dermatologist dermatologist, @PathVariable Long dermId) {
        Days days = new Days();
        days.setDermatologist((Dermatologist) this.userService.findById(dermId));
        days.setVacation(false);
        model.addAttribute("newDays", days);
        return new ModelAndView("addDermatologistWorkDays");
    }

}
