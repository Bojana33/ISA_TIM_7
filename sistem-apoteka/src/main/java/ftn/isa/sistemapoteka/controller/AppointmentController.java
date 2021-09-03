package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.authFacade.AuthenticationFacade;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.service.impl.AppointmentServiceImpl;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @GetMapping("/chooseTime")
    public ModelAndView choose(Model model) throws Exception {
        model.addAttribute("principal", this.userService.getPatientFromPrincipal());
        model.addAttribute("preferredDate", LocalDate.now());
        model.addAttribute("preferredTime", LocalDateTime.now());

        return new ModelAndView("views/enterAppointmentTime");
    }

    @PostMapping("/chooseTime")
    public ModelAndView chooseTime(Model model, @RequestParam("preferredTime") String preferredTime) {


        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a");
        LocalDateTime ldt = LocalDateTime.parse(preferredTime, timeFormatter);

        //List<Pharmacy> available = this.pharmacyService.findWithAvailablePharmacists(ldt);
        model.addAttribute("preferredTime", preferredTime);
        return new ModelAndView("redirect:/appointments/choosePharmacy");
    }

    @GetMapping("/choosePharmacy")
    public  ModelAndView choosePharmacy(Model model, @RequestParam(value="preferredTime", required = false) String preferredTime) throws Exception {
        model.addAttribute("principal", this.userService.getPatientFromPrincipal());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a");
        LocalDateTime ldt = LocalDateTime.parse(preferredTime, timeFormatter);

        List<Pharmacy> pharmacies = this.pharmacyService.findWithAvailablePharmacists(ldt);
        model.addAttribute("pharmacies", pharmacies);
        model.addAttribute("preferredTime", preferredTime);

        return new ModelAndView("views/choosePharmacy");
    }

    @GetMapping("/choosePharmacist")
    public ModelAndView choosePharmacist(@RequestParam("pharmacyId") Long id,
                                         @RequestParam("preferredTime") String preferredTime, Model model) throws Exception {
        Pharmacy pharmacy = this.pharmacyService.findById(id);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a");
        LocalDateTime ldt = LocalDateTime.parse(preferredTime, timeFormatter);

        List<Pharmacist> available = this.userService.findAvailablePharmacist(pharmacy.getId(), ldt);

        model.addAttribute("principal", this.userService.getPatientFromPrincipal());
        model.addAttribute("pharmacists", available);

        Random r = new Random();
        double randomValue = 1 + (5 - 1) * r.nextDouble();

        model.addAttribute("rating", randomValue);
        model.addAttribute("preferredTime", preferredTime);
        model.addAttribute("pharmacyId", id);

        return new ModelAndView("views/availablePharmacists");
    }

    @GetMapping ("/schedule/pharmacist")
    public ModelAndView makeAppointmentPharmacists(@RequestParam("phId") Long phId, @RequestParam("pharmacyId") Long id,
                                                   Model model, @RequestParam("preferredTime") String preferredTime) throws Exception {
        Pharmacy pharmacy = this.pharmacyService.findById(id);
        Pharmacist pharmacist = this.userService.findPharmacistById(phId);
        Patient pat = this.userService.getPatientFromPrincipal();
        model.addAttribute("principal", pat);
        model.addAttribute("preferredTime", preferredTime);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a");
        LocalDateTime ldt = LocalDateTime.parse(preferredTime, timeFormatter);

        // schedule
        Appointment app = new Appointment();
        app.setAdvising(true);
        app.setPatient(pat);
        app.setPharmacy(pharmacy);
        app.setStartingTime(ldt);
        app.setDurationInMinutes(30.0);
        app.setPrice(pharmacy.getAdvisingPrice());
        app.setPharmacist(pharmacist);
        app.setScheduled(true);

        this.appointmentService.save(app);
        this.appointmentService.sendEmailforAdvising(app);

        return new ModelAndView("views/pharmaAppoSuccess");
    }

}
