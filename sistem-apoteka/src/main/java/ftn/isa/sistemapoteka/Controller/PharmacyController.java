package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;
}
