package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.service.PharmacyService;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/pharmacies")
public class PharmacyController {

    private PharmacyService pharmacyService;
    private UserServiceImpl userService;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService, UserServiceImpl userService){
        this.pharmacyService = pharmacyService;
        this.userService = userService;
    }

    @GetMapping(value = "/registerPharmacy")
    public ModelAndView registerPharmacyForm(Model model, HttpServletRequest request){
//        if (request.getSession().getAttribute("email")== null){
//            throw new AccessDeniedException("Access denied");
//        }
//        User user = this.userService.findByEmail(request.getSession().getAttribute("email").toString());
//        if (!userService.isAuthorized(user, "ROLE_SYS_ADMIN")){
//            throw new AccessDeniedException("Access denied");
//        }
        Pharmacy pharmacy = new Pharmacy();
        model.addAttribute(pharmacy);
        return new ModelAndView("registerPharmacy");
    }

    @PostMapping("/registerPharmacy/submit")
    public ModelAndView registerPharmacySubmit(@ModelAttribute Pharmacy pharmacy) throws Exception{
        this.pharmacyService.save(pharmacy);
        return new ModelAndView("redirect:/user/registerPharmacyAdmin/" + pharmacy.getId());
    }

    @GetMapping("/allPharmacies")
    public ModelAndView getAllPharmacies(Model model, String keyword) {
        if (keyword != null) {
            model.addAttribute("pharmacies", this.pharmacyService.findByKeyword(keyword));
        } else
        {
            model.addAttribute("pharmacies", this.pharmacyService.findAll());
        }
        return new ModelAndView("pharmacies");
    }

}
