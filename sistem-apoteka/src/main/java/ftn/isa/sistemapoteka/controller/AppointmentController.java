package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.authFacade.AuthenticationFacade;
import ftn.isa.sistemapoteka.model.Appointment;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.service.impl.AppointmentServiceImpl;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("appointments")
public class AppointmentController {

    private AppointmentServiceImpl appointmentService;
    private PharmacyServiceImpl pharmacyService;
    private UserServiceImpl userService;

    @Autowired
    public AppointmentController(AppointmentServiceImpl appointmentService, PharmacyServiceImpl pharmacyService,
                                 UserServiceImpl userService) {
      this.appointmentService = appointmentService;
      this.pharmacyService = pharmacyService;
      this.userService = userService;
    }

    @GetMapping("/{id}/timeSlots")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView getTimeSlotsForDermatologist(@PathVariable Long id, Model model) throws Exception{
        model.addAttribute("appointments", this.appointmentService.findAllByPharmacyAndAdvising(id,false));
        model.addAttribute("pharmacy", this.pharmacyService.findById(id));
        model.addAttribute("principal", this.userService.getPatientFromPrincipal());

        return new ModelAndView("views/appointmentSlots");
    }

    @GetMapping("/{phId}/schedule/{appId}/{patId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView scheduleAppointmentWithDermatologist(@PathVariable("phId") Long phId,
                                                             @PathVariable("appId") Long appId,
                                                             @PathVariable("patId") Long patId) throws Exception {

        Pharmacy ph = this.pharmacyService.findById(phId);
        Appointment app = this.appointmentService.findById(appId);

        this.appointmentService.makeAppointment(app, ph, patId);
        this.appointmentService.sendEmail(app);
        return new ModelAndView("redirect:/appointments/" + phId + "/timeSlots");
    }

    @GetMapping("/{phId}/cancelAppointment/{appId}/{patId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView cancelAppointmentWithDermatologist(@PathVariable("phId") Long phId,
                                                           @PathVariable("appId") Long appId, Model model,
                                                           @PathVariable("patId") Long patId) throws Exception {

        Pharmacy ph = this.pharmacyService.findById(phId);
        Appointment app = this.appointmentService.findById(appId);

        model.addAttribute("patient", this.userService.getPatientFromPrincipal());
        // TODO: proveri da li je istekao tok za otkazivanje
        // NE RADI
        boolean cancel = this.appointmentService.canBeCanceled(appId);
        if (!cancel) {
            model.addAttribute("appointment", true);
            return new ModelAndView("views/reservationCancelError");
        }

        this.appointmentService.cancelAppointment(app, ph, patId);

        return new ModelAndView("redirect:/appointments/scheduledAppointments/" + patId.toString());
    }

    @GetMapping("/scheduledAppointments/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView getScheduledAppointments(@PathVariable Long id, Model model) throws Exception {

        List<Appointment> scheduled = this.appointmentService.findScheduledByPatient(id);
        model.addAttribute("appointments", scheduled);
        model.addAttribute("principal", this.userService.getPatientFromPrincipal());

        return new ModelAndView("views/scheduledAppointments");
    }

}
