package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.LoyaltyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoyaltyProgramController {

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;
}
