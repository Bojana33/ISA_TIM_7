package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.exception.ResourceConflictException;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.model.PharmacyAdministrator;
import ftn.isa.sistemapoteka.service.PharmacyService;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping(value = "/pharmacies", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacyController {

    private PharmacyServiceImpl pharmacyServiceImpl;

    @Autowired
    public PharmacyController(PharmacyServiceImpl pharmacyServiceImpl){
        this.pharmacyServiceImpl = pharmacyServiceImpl;
    }

    @PostMapping(value = "/registerPharmacy")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public Pharmacy registerPharmacy(@RequestBody Pharmacy pharmacy){
        return this.pharmacyServiceImpl.save(pharmacy);
    }

}
