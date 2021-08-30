package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.service.DrugReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/drugReservations")
public class DrugReservationController {

    private DrugReservationService drugReservationService;

    @Autowired
    public DrugReservationController(DrugReservationService drugReservationService){
        this.drugReservationService = drugReservationService;
    }
}
