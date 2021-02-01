package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pharmacies")
public class PharmacyController {

    private PharmacyService pharmacyService;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService){
        this.pharmacyService = pharmacyService;
    }
}
