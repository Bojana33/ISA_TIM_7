package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.service.PharmacyService;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @GetMapping("/{id}")
    public ModelAndView showPharmacyPage(@PathVariable Long id, Model model) throws Exception {
        if (this.pharmacyService.findById(id) == null) { throw new Exception("Pharmacy with this id does not exist"); }
        model.addAttribute("pharmacy", this.pharmacyService.findById(id));
        model.addAttribute("principal", this.userService.getPatientFromPrincipal());

        return new ModelAndView("views/pharmacy");
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
        return new ModelAndView("views/registerPharmacy");
    }

    @PostMapping("/registerPharmacy/submit")
    public ModelAndView registerPharmacySubmit(@ModelAttribute Pharmacy pharmacy) throws Exception{
        this.pharmacyService.save(pharmacy);
        return new ModelAndView("redirect:/user/registerPharmacyAdmin/" + pharmacy.getId());
    }

    @GetMapping("/allPharmacies")
    public ModelAndView getAllPharmacies(Model model, String keyword) throws Exception {
        if (keyword != null) {
            model.addAttribute("pharmacies", this.pharmacyService.findByKeyword(keyword));
        } else
        {
            model.addAttribute("pharmacies", this.pharmacyService.findAll());
        }

        try {
            model.addAttribute("principal", this.userService.getPatientFromPrincipal());
            return new ModelAndView("views/pharmacies");
        } catch (Exception e) {
            return new ModelAndView("views/pharmaciesGuests");
        }

        /*model.addAttribute("principal", this.userService.getPatientFromPrincipal());
        return new ModelAndView("views/pharmacies");*/
    }

    @GetMapping("/allPharmacies/sortByNameAsc")
    public ModelAndView sortByNameAsc(Model model) throws Exception {
        List<Pharmacy> sorted = this.pharmacyService.orderByNameAsc();
        model.addAttribute("pharmacies", sorted);

        try {
            model.addAttribute("principal", this.userService.getPatientFromPrincipal());
            return new ModelAndView("views/pharmacies");
        } catch (Exception e) {
            return new ModelAndView("views/pharmaciesGuests");
        }

    }

    @GetMapping("/allPharmacies/sortByNameDesc")
    public ModelAndView sortByNameDesc(Model model) throws Exception {
        List<Pharmacy> sorted = this.pharmacyService.orderByNameDesc();
        model.addAttribute("pharmacies", sorted);

        try {
            model.addAttribute("principal", this.userService.getPatientFromPrincipal());
            return new ModelAndView("views/pharmacies");
        } catch (Exception e) {
            return new ModelAndView("views/pharmaciesGuests");
        }
    }

    @GetMapping("/allPharmacies/sortByRatingDesc")
    public ModelAndView sortByRatingDesc(Model model) throws Exception {
        List<Pharmacy> sorted = this.pharmacyService.orderByRatingDesc();
        model.addAttribute("pharmacies", sorted);

        try {
            model.addAttribute("principal", this.userService.getPatientFromPrincipal());
            return new ModelAndView("views/pharmacies");
        } catch (Exception e) {
            return new ModelAndView("views/pharmaciesGuests");
        }
    }

    @GetMapping("/allPharmacies/sortByRatingAsc")
    public ModelAndView sortByRatingAsc(Model model) throws Exception {
        List<Pharmacy> sorted = this.pharmacyService.orderByRatingAsc();
        model.addAttribute("pharmacies", sorted);

        try {
            model.addAttribute("principal", this.userService.getPatientFromPrincipal());
            return new ModelAndView("views/pharmacies");
        } catch (Exception e) {
            return new ModelAndView("views/pharmaciesGuests");
        }
    }

}
