package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.service.AppointmentService;
import ftn.isa.sistemapoteka.service.impl.AppointmentServiceImpl;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("appointments")
public class AppointmentController {

    private AppointmentServiceImpl appointmentService;
    private PharmacyServiceImpl pharmacyService;

    @Autowired
    public AppointmentController(AppointmentServiceImpl appointmentService, PharmacyServiceImpl pharmacyService){
      this.appointmentService = appointmentService;
      this.pharmacyService = pharmacyService;
    }

    @GetMapping("/{id}/timeSlots")
    public ModelAndView getTimeSlots(@PathVariable Long id, Model model) {
        model.addAttribute("appointments", this.appointmentService.findAll());
        model.addAttribute("pharmacy", this.pharmacyService.findById(id));

        return new ModelAndView("views/appointmentSlots");
    }
}
