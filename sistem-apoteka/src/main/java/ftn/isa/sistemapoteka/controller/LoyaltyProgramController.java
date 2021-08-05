package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.service.LoyaltyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("loyaltyPrograms")
public class LoyaltyProgramController {

    private LoyaltyProgramService loyaltyProgramService;

    @Autowired
    public LoyaltyProgramController(LoyaltyProgramService loyaltyProgramService){
        this.loyaltyProgramService = loyaltyProgramService;
    }
}
