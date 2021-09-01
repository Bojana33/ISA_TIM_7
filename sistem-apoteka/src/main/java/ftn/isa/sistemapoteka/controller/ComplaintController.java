package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.service.ComplaintService;
import ftn.isa.sistemapoteka.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("complaints")
public class ComplaintController {

    private ComplaintServiceImpl complaintService;
    private UserServiceImpl userService;
    private PharmacyServiceImpl pharmacyService;
    private DrugReservationServiceImpl drugReservationService;
    private ConsultationServiceImpl consultationService;
    private AppointmentServiceImpl appointmentService;

    @Autowired
    public ComplaintController(ComplaintServiceImpl complaintService, UserServiceImpl userService, PharmacyServiceImpl pharmacyService,DrugReservationServiceImpl drugReservationService, ConsultationServiceImpl consultationService, AppointmentServiceImpl appointmentService){
        this.complaintService = complaintService;
        this.userService = userService;
        this.pharmacyService = pharmacyService;
        this.drugReservationService = drugReservationService;
        this.consultationService = consultationService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/getAllComplaints")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView getComplaints(Model model){
        model.addAttribute("complaints", this.complaintService.findAllComplaints());
        return new ModelAndView("complaints");
    }
    @GetMapping("/complaintFor")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView complaintForForm(Model model){
        Complaint complaint = new Complaint();
        model.addAttribute("complaint", complaint);
        return new ModelAndView("complaint-for");
    }

    @PostMapping("/complaintFor/submit")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView complaintFor(Model model, @ModelAttribute Complaint complaint){
        Complaint c = this.complaintService.saveComplaint(complaint);
        model.addAttribute("complaint", c);
        return new ModelAndView("redirect:/complaints/writeComplaint/" + c.getId());
    }

    @GetMapping("/writeComplaint/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView writeComplaintForm(Model model, @PathVariable Long id, Authentication authentication) throws Exception{
        Complaint complaint = this.complaintService.findById(id);
        Patient patient = (Patient) this.userService.findByEmail(authentication.getName());
        List<Consultation> consultations = this.consultationService.findAllByPatient(patient);
        List<Appointment> appointments = this.appointmentService.findAllByPatient(patient);
        if(complaint.getComplaintType()==ComplaintType.PHARMACY){
            List<DrugReservation> drugReservations = this.drugReservationService.findAllByPatient(patient);
            List<Pharmacy> pharmacies = this.pharmacyService.findPharmaciesByDrugReservationsConsultationsAppointments(drugReservations,consultations,appointments);
            model.addAttribute("pharmacies", pharmacies);
        } else if(complaint.getComplaintType()==ComplaintType.PHARMACIST){
            List<User> pharmacists = this.userService.findPharmacistsByConsultations(consultations);
            model.addAttribute("pharmacists", pharmacists);
        } else {
            List<User> dermatologists = this.userService.findDermatologistsByAppointments(appointments);
            model.addAttribute("dermatologists", dermatologists);
        }
        model.addAttribute("complaint",complaint);
        return new ModelAndView("write-complaint");
    }

    @PostMapping("/writeComplaint/{id}/submit/{type}")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView sendComplaint(Authentication authentication, @ModelAttribute Complaint complaint, @PathVariable ComplaintType type){
        Patient patient = (Patient) this.userService.findByEmail(authentication.getName());
        complaint.setPatient(patient);
        complaint.setComplaintType(type);
        this.complaintService.updateComplaint(complaint);
        return new ModelAndView("redirect:/user/patient/home");
    }

    @GetMapping("/sendResponse/{id}")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView sendResponseForm(Model model, @PathVariable Long id) throws Exception{
        Complaint complaint = this.complaintService.findById(id);
        model.addAttribute("complaint", complaint);
        return new ModelAndView("complaint-response");
    }

    @PostMapping("/sendResponse/{id}/submit")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView sendResponse(@ModelAttribute Complaint complaint, @PathVariable Long id) throws Exception{
        Complaint complaint1 = this.complaintService.findById(id);
        complaint1.setIsAnswered(true);
        complaint1.setResponse(complaint.getResponse());
        this.complaintService.updateComplaint(complaint1);
        this.complaintService.sendResponse(complaint1);
        return new ModelAndView("redirect:/user/sys-admin/home");
    }

    @GetMapping("/readComplaint/{id}")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView readComplaint(Model model, @PathVariable Long id) throws Exception{
        Complaint complaint = this.complaintService.findById(id);
        model.addAttribute("complaint", complaint);
        return new ModelAndView("complaint-read");
    }

}
