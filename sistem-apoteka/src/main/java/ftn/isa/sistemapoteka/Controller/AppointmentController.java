package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
}
