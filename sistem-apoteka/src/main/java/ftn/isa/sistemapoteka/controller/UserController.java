package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.exception.ResourceConflictException;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private UserServiceImpl userService;
    private PharmacyServiceImpl pharmacyService;

    @Autowired
    public UserController(UserServiceImpl userService, PharmacyServiceImpl pharmacyService){
        this.pharmacyService = pharmacyService;
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('SYS_ADMIN','PHARMACY_ADMIN')")
    public User loadById(@PathVariable Long userId) {
        return this.userService.findById(userId);
    }

    @GetMapping("/patientHome/{id}")
    public ModelAndView showProfile(@PathVariable Long id, Model model) throws Exception {
        Patient patient = (Patient) this.userService.findById(id);
        if (patient == null) { throw new Exception("Page does not exist"); }

        // TODO: Odradi proveru da l` je ulogovan
        // TODO: Razmisli da li treba poseban servis za pacijenta\

        model.addAttribute("patient", patient);
        return new ModelAndView("views/patientProfile");
    }

    /*@GetMapping("/{id}/loyaltyProgramDetails")
    public ModelAndView showLoyaltyProgramDetails(@PathVariable Long id, Model model) throws Exception{
        Patient patient = this.userService.findPatientById(id);
        if(patient == null) { throw new Exception("Patient does not exist."); }

        model.addAttribute("program", patient);

        return new ModelAndView("views/showLoyaltyDetails");
    }*/

    @GetMapping("/allergyTriggers/{id}")
    public ModelAndView showAllergies(@PathVariable Long id, Model model) throws Exception{
        Patient patient = this.userService.findPatientById(id);
        if(patient == null) { throw new Exception("Patient does not exist."); }

        model.addAttribute("drugs", patient.getAllergies());

        return new ModelAndView("views/showAllergies");
    }

    @GetMapping("/addAllergyTrigger/{id}")
    public ModelAndView addAllergy(@PathVariable Long id, Model model) throws Exception {
        Patient patient = this.userService.findPatientById(id);
        if (patient == null) { throw new Exception("Patient with this id does not exist"); }
        model.addAttribute("patient", patient);
        model.addAttribute("drug");

        return new ModelAndView("views/addAllergy");
    }

    @PostMapping("/addAllergyTrigger/{id}/submit")
    public ModelAndView addAllergy(@PathVariable Long id, Model model, @ModelAttribute Patient patient,
                                                                    @ModelAttribute Drug drug) throws Exception {
        try {
            // TODO: Napravi metod za dodavanje alergija
            // da li to treba u drugom servisu?
            //this.userService.addAllergyTriggerToPatient(patient, drug);
            model.addAttribute("patient", patient);
            model.addAttribute("drug", drug);
            return new ModelAndView("redirect:/user/yourAllergies/" + patient.getId());
        } catch (Exception e) {
            return new ModelAndView("redirect:/auth/home");
        }// TODO: napravi stranicu i get metodu
    }

    @GetMapping("/registerPharmacyAdmin/{pharmacyId}")
    public ModelAndView rpa(Model model, @PathVariable Long pharmacyId){
        PharmacyAdministrator pharmacyAdministrator = new PharmacyAdministrator();
        model.addAttribute(pharmacyAdministrator);
        model.addAttribute(pharmacyId);
        return new ModelAndView("views/registerPharmacyAdmin");
    }

    @PostMapping(value = "/registerPharmacyAdmin/{pharmacyId}/submit")
    public ModelAndView registerPharmacyAdmin(@ModelAttribute PharmacyAdministrator user, @PathVariable Long pharmacyId){
        if (this.userService.findByEmail(user.getEmail()) != null) {
            throw new ResourceConflictException(user.getId(), "Email already exists");
        }
        user.setPharmacy(this.pharmacyService.findById(pharmacyId));
        this.userService.savePharmacyAdmin(user);
        return new ModelAndView("redirect:/auth/sys-admin/home");
    }

    @PostMapping(value = "/registerSupplier")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ResponseEntity<Supplier> registerSupplier(@RequestBody Supplier user){
        if (this.userService.findByEmail(user.getEmail()) != null) {
            throw new ResourceConflictException(user.getId(), "Email already exists");
        }
        return new ResponseEntity<>(this.userService.saveSupplier(user), HttpStatus.CREATED);
    }

    @PostMapping(value = "/registerSystemAdmin")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ResponseEntity<SystemAdministrator> registerSystemAdmin(@RequestBody SystemAdministrator user){
        if (this.userService.findByEmail(user.getEmail()) != null) {
            throw new ResourceConflictException(user.getId(), "Email already exists");
        }
        return new ResponseEntity<>(this.userService.saveSystemAdmin(user), HttpStatus.CREATED);
    }

    @GetMapping("/registerDermatologist")
    public ModelAndView regDermForm(Model model){
        Dermatologist dermatologist = new Dermatologist();
        model.addAttribute(dermatologist);
        return new ModelAndView("views/registerDermatologist");
    }

    @PostMapping(value = "/registerDermatologist/submit")
    public ModelAndView registerDermatologist(@ModelAttribute Dermatologist user){
        if (this.userService.findByEmail(user.getEmail()) != null) {
            throw new ResourceConflictException(user.getId(), "Email already exists");
        }
        this.userService.saveDermatologist(user);
        return new ModelAndView("redirect:/auth/sys-admin/home");
    }

}
