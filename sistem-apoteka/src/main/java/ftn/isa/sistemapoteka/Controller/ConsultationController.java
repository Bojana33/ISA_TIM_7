package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;
}
