package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.authFacade.AuthenticationFacade;
import ftn.isa.sistemapoteka.model.Appointment;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.service.impl.AppointmentServiceImpl;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequestMapping("appointments")
public class AppointmentController {

    private AppointmentServiceImpl appointmentService;
    private PharmacyServiceImpl pharmacyService;
    private AuthenticationFacade facade;

    @Autowired
    public AppointmentController(AppointmentServiceImpl appointmentService, PharmacyServiceImpl pharmacyService,
                                 AuthenticationFacade facade) {
      this.appointmentService = appointmentService;
      this.pharmacyService = pharmacyService;
      this.facade = facade;
    }

    @GetMapping("/{id}/timeSlots")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView getTimeSlots(@PathVariable Long id, Model model) throws Exception{
        model.addAttribute("appointments", this.appointmentService.findAll());
        model.addAttribute("pharmacy", this.pharmacyService.findById(id));
        model.addAttribute("principal", this.facade.getAuthentication().getPrincipal().toString());

        return new ModelAndView("views/appointmentSlots");
    }

    /*@PostMapping("/{id}/timeSlots/scheduleSuccess")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView scheduleAppointment(@PathVariable Long id,
                                            @ModelAttribute Appointment appointment,
                                            @ModelAttribute Pharmacy pharmacy,
                                            @ModelAttribute Principal principal) throws Exception {
        Pharmacy ph = this.pharmacyService.findById(id);
        Appointment app = this.appointmentService.findById(appointment.getId());
        // TODO: Proveri da li vraca pacijenta sa svim poljima ili su neka null

        Appointment a = this.appointmentService.makeAppointment(app, ph, principal);

        // ako se promeni flag, termin se nece prikazati
        return new ModelAndView("redirect:/appointments/" + pharmacy.getId() + "/timeSlots");
        //return new ModelAndView("views/appointmentSlots");
    }*/

    @GetMapping("/{phId}/schedule/{appId}/{email}")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView scheduleApp(@PathVariable("phId") Long phId, @PathVariable("appId") Long appId,
                                    @PathVariable("email") String email) throws Exception {

        Pharmacy ph = this.pharmacyService.findById(phId);
        Appointment app = this.appointmentService.findById(appId);
        String em = email;
        // TODO: Proveri da li vraca pacijenta sa svim poljima ili su neka null
        this.appointmentService.makeAppointment(app, ph, em);

        return new ModelAndView("redirect:/appointments/" + phId + "/timeSlots");
    }

}
