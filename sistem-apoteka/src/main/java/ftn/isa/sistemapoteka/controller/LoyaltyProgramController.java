package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.LoyaltyProgram;
import ftn.isa.sistemapoteka.service.LoyaltyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "loyaltyPrograms")
public class LoyaltyProgramController {

    private LoyaltyProgramService loyaltyProgramService;

    @Autowired
    public LoyaltyProgramController(LoyaltyProgramService loyaltyProgramService){
        this.loyaltyProgramService = loyaltyProgramService;
    }

    @PostMapping(value = "/defineLoyaltyProgram")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ResponseEntity<LoyaltyProgram> defineLP(@RequestBody LoyaltyProgram loyaltyProgram) throws Exception{
        return new ResponseEntity<>(this.loyaltyProgramService.saveLP(loyaltyProgram), HttpStatus.CREATED);
    }
}
