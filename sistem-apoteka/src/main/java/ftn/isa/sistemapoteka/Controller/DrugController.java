package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrugController {

    @Autowired
    private DrugService drugService;
}
