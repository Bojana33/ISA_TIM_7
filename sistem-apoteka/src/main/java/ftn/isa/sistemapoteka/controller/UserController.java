package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.exception.ResourceConflictException;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.service.UserService;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
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

    @GetMapping("/registerPharmacyAdmin/{pharmacyId}")
    public ModelAndView rpa(Model model, @PathVariable Long pharmacyId){
        PharmacyAdministrator pharmacyAdministrator = new PharmacyAdministrator();
        model.addAttribute(pharmacyAdministrator);
        model.addAttribute(pharmacyId);
        return new ModelAndView("registerPharmacyAdmin");
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
    public ResponseEntity<SystemAdministrator> registerPharmacyAdmin(@RequestBody SystemAdministrator user){
        if (this.userService.findByEmail(user.getEmail()) != null) {
            throw new ResourceConflictException(user.getId(), "Email already exists");
        }
        return new ResponseEntity<>(this.userService.saveSystemAdmin(user), HttpStatus.CREATED);
    }

    @PostMapping(value = "/registerDermatologist")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ResponseEntity<Dermatologist> registerDermatologist(@RequestBody Dermatologist user){
        if (this.userService.findByEmail(user.getEmail()) != null) {
            throw new ResourceConflictException(user.getId(), "Email already exists");
        }
        return new ResponseEntity<>(this.userService.saveDermatologist(user), HttpStatus.CREATED);
    }

}
