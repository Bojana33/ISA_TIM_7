package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("consultations")
public class ConsultationController {

    private ConsultationService consultationService;

    @Autowired
    public ConsultationController(ConsultationService consultationService){
        this.consultationService = consultationService;
    }
}
