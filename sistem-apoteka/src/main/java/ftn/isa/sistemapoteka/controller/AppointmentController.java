package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.Appointment;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.model.Pharmacist;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.service.impl.AppointmentServiceImpl;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("appointments")
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;
    private final PharmacyServiceImpl pharmacyService;
    private final UserServiceImpl userService;

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

        boolean cancel = this.appointmentService.canBeCanceled(appId);
        if (!cancel) {
            model.addAttribute("appointment", true);
            return new ModelAndView("views/reservationCancelError");
        }

        this.appointmentService.cancelAppointment(app, ph, patId);

        return new ModelAndView("redirect:/appointments/dermatologist/upcoming");
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
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView choose(Model model) throws Exception {
        model.addAttribute("principal", this.userService.getPatientFromPrincipal());
        model.addAttribute("preferredDate", LocalDate.now());
        model.addAttribute("preferredTime", LocalDateTime.now());

        return new ModelAndView("views/enterAppointmentTime");
    }

    @PostMapping("/chooseTime")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView chooseTime(Model model, @RequestParam("preferredTime") String preferredTime) {


        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a");
        LocalDateTime ldt = LocalDateTime.parse(preferredTime, timeFormatter);

        //List<Pharmacy> available = this.pharmacyService.findWithAvailablePharmacists(ldt);
        model.addAttribute("preferredTime", preferredTime);
        return new ModelAndView("redirect:/appointments/choosePharmacy");
    }

    @GetMapping("/choosePharmacy")
    @PreAuthorize("hasRole('PATIENT')")
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
    @PreAuthorize("hasRole('PATIENT')")
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
    @PreAuthorize("hasRole('PATIENT')")
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

    @GetMapping("/pharmacist/upcoming")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView showActiveAppWithPharmacist(Model model) throws Exception {
        Patient patient = this.userService.getPatientFromPrincipal();
        model.addAttribute("principal", patient);
        model.addAttribute("derma", false);
        model.addAttribute("history", false);
        model.addAttribute("appointments", this.appointmentService.getUpcomingWithPharmacist());

        return new ModelAndView("views/upcomingWithPharmacist");
    }

    @GetMapping("/pharmacist/history")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView showPastAppWithPharmacist(Model model) throws Exception {
        Patient patient = this.userService.getPatientFromPrincipal();
        model.addAttribute("history", true);
        model.addAttribute("derma", false);
        model.addAttribute("principal", this.userService.getPatientFromPrincipal());
        model.addAttribute("appointments", this.appointmentService.getPastOnesWithPharmacist());

        return new ModelAndView("views/upcomingWithPharmacist");
    }

    @GetMapping("/pharmacist/cancel/{appId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView cancelAppWithPharmacist(@PathVariable("appId") Long appId, Model model) throws Exception {
        Appointment toCancel = this.appointmentService.findById(appId);
        Patient patient = this.userService.getPatientFromPrincipal();
        model.addAttribute("principal", patient);
        model.addAttribute("history", false);

        boolean cancel = this.appointmentService.canBeCanceled(appId);
        if (!cancel) {
            model.addAttribute("appointment", true);
            return new ModelAndView("views/reservationCancelError");
        }

        toCancel.setDeleted(true);
        this.appointmentService.update(toCancel);

        model.addAttribute("appointments", this.appointmentService.getUpcomingWithPharmacist());

        return new ModelAndView("redirect:/appointments/pharmacist/upcoming");
    }

    @GetMapping("/dermatologist/history")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView showPastAppWithDermatologist(Model model) throws Exception {
        Patient patient = this.userService.getPatientFromPrincipal();
        model.addAttribute("history", true);
        model.addAttribute("derma", true);
        model.addAttribute("principal", this.userService.getPatientFromPrincipal());
        model.addAttribute("appointments", this.appointmentService.getPastOnesWithDermatologist());

        return new ModelAndView("views/upcomingWithPharmacist");
    }

    @GetMapping("/dermatologist/upcoming")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView showActiveAppWithDermatologist(Model model) throws Exception {
        Patient patient = this.userService.getPatientFromPrincipal();
        model.addAttribute("principal", patient);
        model.addAttribute("derma", true);
        model.addAttribute("history", false);
        model.addAttribute("appointments", this.appointmentService.getUpcomingWithDermatologist());

        return new ModelAndView("views/upcomingWithPharmacist");
    }
}
