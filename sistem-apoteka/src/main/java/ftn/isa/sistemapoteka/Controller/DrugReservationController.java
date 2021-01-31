package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.DrugReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrugReservationController {

    @Autowired
    private DrugReservationService drugReservationService;
}
