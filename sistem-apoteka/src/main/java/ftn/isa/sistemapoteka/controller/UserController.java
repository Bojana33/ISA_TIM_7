package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.exception.ResourceConflictException;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.service.UserService;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

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

    @GetMapping("/index")
    @PreAuthorize("hasAnyRole('SYS_ADMIN','PATIENT','SUPPLIER')")
    public ModelAndView indexPage(Authentication auth) throws Exception{
        User u = this.userService.findByEmail(auth.getName());
        if (u.getEnabled()==false){
            throw new Exception("Your account is not activated, please check your email!");
        }
        if (u.getIsFirstLogin()){
            u.setIsFirstLogin(false);
            return new ModelAndView("redirect:/auth/change-password");
        }
        return new ModelAndView("indexPage");
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('SYS_ADMIN','PHARMACY_ADMIN')")
    public User loadById(@PathVariable Long userId) {
        return this.userService.findById(userId);
    }

    @GetMapping("/patientHome/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView showProfile(@PathVariable Long id, Model model) throws Exception {
        Patient patient = (Patient) this.userService.findById(id);
        if (patient == null) { throw new Exception("Page does not exist"); }

        model.addAttribute("patient", patient);
        return new ModelAndView("views/patientProfile");
    }

    @GetMapping("/{id}/allergyTriggers")
    public ModelAndView showAllergies(@PathVariable Long id, Model model) throws Exception{
        Patient patient = this.userService.findPatientById(id);
        if(patient == null) { throw new Exception("Patient does not exist."); }

        model.addAttribute("drugs", patient.getAllergies());

        return new ModelAndView("views/showAllergies");
    }

    @GetMapping("/registerPharmacyAdmin/{pharmacyId}")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView rpa(Model model, @PathVariable Long pharmacyId){
        PharmacyAdministrator pharmacyAdministrator = new PharmacyAdministrator();
        model.addAttribute(pharmacyAdministrator);
        model.addAttribute(pharmacyId);
        return new ModelAndView("views/registerPharmacyAdmin");
    }

    @PostMapping(value = "/registerPharmacyAdmin/{pharmacyId}/submit")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView registerPharmacyAdmin(@ModelAttribute PharmacyAdministrator user, @PathVariable Long pharmacyId){
        if (this.userService.findByEmail(user.getEmail()) != null) {
            throw new ResourceConflictException(user.getId(), "Email already exists");
        }
        user.setPharmacy(this.pharmacyService.findById(pharmacyId));
        this.userService.savePharmacyAdmin(user);
        return new ModelAndView("redirect:/user/sys-admin/home");
    }

    @GetMapping("/registerSupplier")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView regSupForm(Model model){
        Supplier supplier = new Supplier();
        model.addAttribute(supplier);
        return new ModelAndView("registerSupplier");
    }

    @PostMapping(value = "/registerSupplier/submit")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView registerSupplier(@ModelAttribute Supplier user){
        if (this.userService.findByEmail(user.getEmail()) != null) {
            throw new ResourceConflictException(user.getId(), "Email already exists");
        }
        this.userService.saveSupplier(user);
        return new ModelAndView("redirect:/user/sys-admin/home");
    }

    @GetMapping("/registerSystemAdmin")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView regSysAdminForm(Model model){
        SystemAdministrator systemAdministrator = new SystemAdministrator();
        model.addAttribute(systemAdministrator);
        return new ModelAndView("registerSystemAdmin");
    }

    @PostMapping(value = "/registerSystemAdmin/submit")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView registerSystemAdmin(@ModelAttribute SystemAdministrator user){
        if (this.userService.findByEmail(user.getEmail()) != null) {
            throw new ResourceConflictException(user.getId(), "Email already exists");
        }
        this.userService.saveSystemAdmin(user);
        return new ModelAndView("redirect:/user/sys-admin/home");
    }

    @GetMapping("/registerDermatologist")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView regDermForm(Model model){
        Dermatologist dermatologist = new Dermatologist();
        model.addAttribute(dermatologist);
        return new ModelAndView("views/registerDermatologist");
    }

    @PostMapping(value = "/registerDermatologist/submit")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView registerDermatologist(@ModelAttribute Dermatologist user){
        if (this.userService.findByEmail(user.getEmail()) != null) {
            throw new ResourceConflictException(user.getId(), "Email already exists");
        }
        this.userService.saveDermatologist(user);
        return new ModelAndView("redirect:/user/sys-admin/home");
    }

    @GetMapping("/sys-admin/home")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView sysAdminHome(){
        return new ModelAndView("views/sys-admin-home");
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/patient/home")
    public ModelAndView patientHome(){ return new ModelAndView("views/patient-home"); }

    @PreAuthorize("hasRole('SUPPLIER')")
    @GetMapping("/supplier/home")
    public ModelAndView supplierHome(){
        return new ModelAndView("views/supplier-home");
    }


}
