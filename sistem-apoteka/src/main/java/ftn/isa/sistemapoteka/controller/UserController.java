package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.exception.ResourceConflictException;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('SYS_ADMIN','PHARMACY_ADMIN')")
    public User loadById(@PathVariable Long userId) {
        return this.userService.findById(userId);
    }

    @PostMapping(value = "/registerPharmacyAdmin/{pharmacyId}")
    public ModelAndView registerPharmacyAdmin(@RequestBody PharmacyAdministrator user, @PathVariable Long pharmacyId){
        if (this.userService.findByEmail(user.getEmail()) != null) {
            throw new ResourceConflictException(user.getId(), "Email already exists");
        }
        this.userService.savePharmacyAdmin(user,pharmacyId);
        return new ModelAndView("redirect:/auth/sys-admin/home");
    }

    @PostMapping(value = "/registerSupplier")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ResponseEntity<Supplier> registerPharmacyAdmin(@RequestBody Supplier user){
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
