package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService){
      this.appointmentService = appointmentService;
    }

    @PreAuthorize("hasRole('DERMATOLOGIST')")
    @GetMapping("/dermatologist/calendar")
    public ModelAndView dermatologistCalendar(){
        return new ModelAndView("Calendar1");
    }
}
