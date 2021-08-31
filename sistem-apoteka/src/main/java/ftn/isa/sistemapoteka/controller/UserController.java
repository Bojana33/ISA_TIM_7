package ftn.isa.sistemapoteka.controller;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.IOException;
import com.itextpdf.io.source.ByteArrayOutputStream;
import ftn.isa.sistemapoteka.exception.ResourceConflictException;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.service.UserService;
import ftn.isa.sistemapoteka.service.impl.LoyaltyProgramServiceImpl;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserServiceImpl userService;
    private PharmacyServiceImpl pharmacyService;
    private LoyaltyProgramServiceImpl loyaltyProgramService;

    @Autowired
    public UserController(UserServiceImpl userService, PharmacyServiceImpl pharmacyService, LoyaltyProgramServiceImpl loyaltyProgramService){
        this.pharmacyService = pharmacyService;
        this.userService = userService;
        this.loyaltyProgramService = loyaltyProgramService;
    }

    @GetMapping("/index")
    @PreAuthorize("hasAnyRole('SYS_ADMIN','PATIENT','SUPPLIER','DERMATOLOGIST','PHARMACIST')")
    public ModelAndView indexPage(Authentication auth) throws Exception{
        User u = this.userService.findByEmail(auth.getName());
        if (u.getEnabled()==false){
            throw new Exception("Your account is not activated, please check your email!");
        }
        if (u.getIsFirstLogin()){
            u.setIsFirstLogin(false);
            return new ModelAndView("redirect:/auth/change-password");
        }
        if (u instanceof Patient){
            this.userService.defineUserCategory((Patient) u);
        }
        return new ModelAndView("indexPage");
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('SYS_ADMIN','PHARMACY_ADMIN','DERMATOLOGIST','PHARMACIST')")
    public User loadById(@PathVariable Long userId) {
        return this.userService.findById(userId);
    }

    @GetMapping("/sys-admin/home")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView sysAdminHome(Model model){
        LoyaltyProgram loyaltyProgram = this.loyaltyProgramService.getLP(1L);
        model.addAttribute("loyaltyProgram", loyaltyProgram);
        return new ModelAndView("sys-admin-home");
    }

    @PreAuthorize("hasRole('DERMATOLOGIST')")
    @GetMapping("/dermatologist/home")
    public ModelAndView dermatologistHome(){
        return new ModelAndView("dermatologist-home");
    }

    @PreAuthorize("hasRole('PHARMACIST')")
    @GetMapping("/pharmacist/home")
    public ModelAndView pharmacistHome(){
        return new ModelAndView("pharmacist-home");
    }


    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/patient/home")
    public ModelAndView patientHome(){
        return new ModelAndView("patient-home");
    }

    @PreAuthorize("hasRole('SUPPLIER')")
    @GetMapping("/supplier/home")
    public ModelAndView supplierHome(){
        return new ModelAndView("supplier-home");
    }

    @GetMapping("/registerPharmacyAdmin/{pharmacyId}")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView rpa(Model model, @PathVariable Long pharmacyId){
        PharmacyAdministrator pharmacyAdministrator = new PharmacyAdministrator();
        model.addAttribute(pharmacyAdministrator);
        model.addAttribute(pharmacyId);
        return new ModelAndView("registerPharmacyAdmin");
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
        return new ModelAndView("registerDermatologist");
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

    @GetMapping("/showDermatologist/{id}")
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ModelAndView showDermatologist(@PathVariable Long id, Model model) throws Exception {
        Dermatologist dermatologist= (Dermatologist) this.userService.findById(id);
        if (dermatologist == null) { throw new Exception("Page does not exist"); }

        model.addAttribute("dermatologist", dermatologist);
        return new ModelAndView("dermatologist-profile");
    }

    @GetMapping("/updateProfile")
    @PreAuthorize("hasAnyRole('PATIENT','SYS_ADMIN','SUPPLIER','DERMATOLOGIST','PHARMACIST')")
    public ModelAndView updateProfile(Model model, Authentication authentication){
        User user = this.userService.findByEmail(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("id", user.getId());
        return new ModelAndView("update-profile");
    }

    @PostMapping("/updateProfile/{id}/submit")
    @PreAuthorize("hasAnyRole('PATIENT','SYS_ADMIN','SUPPLIER','DERMATOLOGIST','PHARMACIST')")
    public ModelAndView updateProfileSubmit(Model model, @ModelAttribute User user, @PathVariable Long id){
        this.userService.updateProfile(user);
        model.addAttribute("user", user);
        if (this.userService.findById(id).getUserRole()==UserRole.SUPPLIER){
            return new ModelAndView("redirect:/user/supplier/home");
        }
        return new ModelAndView("redirect:/user/index");
    }

    @GetMapping("/subscribedPharmacies")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView sp(Authentication authentication, Model model){
        Patient patient =(Patient) this.userService.findByEmail(authentication.getName());
        Set<Pharmacy> pharmacies = this.userService.findAllSubscribedPharmacies(patient);
        model.addAttribute("pharmacies",pharmacies);
        return new ModelAndView("subscriptions");
    }


    @GetMapping("/allPatients/sortByFirstNameAsc")
    public ModelAndView sortByNameAsc(Model model) {
        List<Patient> sorted = this.userService.orderByFirstNameAsc();
        model.addAttribute("patients", sorted);

        return new ModelAndView("users");
    }

    @GetMapping("/allPatients/sortByFirstNameDesc")
    public ModelAndView sortByNameDesc(Model model) {
        List<Patient> sorted = this.userService.orderByFirstNameDesc();
        model.addAttribute("users", sorted);

        return new ModelAndView("users");
    }

    @GetMapping("/allPatients/sortByLastNameAsc")
    public ModelAndView sortByLastNameAsc(Model model) {
        List<Patient> sorted = this.userService.orderByLastNameAsc();
        model.addAttribute("patients", sorted);

        return new ModelAndView("clients");
    }

    @GetMapping("/allPatients/sortByLastNameDesc")
    public ModelAndView sortByLastNameDesc(Model model) {
        List<Patient> sorted = this.userService.orderByLastNameDesc();
        model.addAttribute("patients", sorted);

        return new ModelAndView("clients");
    }
}
