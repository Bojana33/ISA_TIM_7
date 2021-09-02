package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.email.EmailService;
import ftn.isa.sistemapoteka.model.Appointment;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.repository.AppointmentRepository;
import ftn.isa.sistemapoteka.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private UserServiceImpl userService;
    private PharmacyServiceImpl pharmacyService;
    private EmailService emailService;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserServiceImpl userService,
                                  PharmacyServiceImpl pharmacyService, EmailService emailService) {
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
        this.pharmacyService = pharmacyService;
        this.emailService = emailService;
    }

    @Override
    public Appointment findById(Long id) throws Exception {
        if (!this.appointmentRepository.findById(id).isPresent()) {
            throw new Exception("No such value(Appointment service)");
        }

        return this.appointmentRepository.findById(id).get();
    }

    @Override
    public List<Appointment> findAll() { return this.appointmentRepository.findAll(); }

    @Override
    public Appointment save(Appointment appointment) throws Exception{
        if (this.appointmentRepository.findById(appointment.getId()).isPresent()){
            throw new Exception("Appointment with that id already exist!");
        }
        return this.appointmentRepository.save(appointment);
    }

    @Override
    public Appointment update(Appointment appointment) throws Exception {
        if (!this.appointmentRepository.findById(appointment.getId()).isPresent()) {
            throw new Exception("No such value (Appointment service update)");
        }
        Appointment forUpdate = this.appointmentRepository.findById(appointment.getId()).get();

        forUpdate.setPharmacy(appointment.getPharmacy());
        forUpdate.setPatient(appointment.getPatient());
        forUpdate.setDermatologist(appointment.getDermatologist());
        /*forUpdate.setDateTimeEnd(appointment.getDateTimeEnd());
        forUpdate.setDateTimeStart(appointment.getDateTimeStart());*/
        forUpdate.setDate(appointment.getDate());
        forUpdate.setDurationInMinutes(appointment.getDurationInMinutes());
        forUpdate.setStartingTime(appointment.getStartingTime());
        forUpdate.setPrice(appointment.getPrice());
        forUpdate.setLoyaltyPoints(appointment.getLoyaltyPoints());
        forUpdate.setScheduled(appointment.getScheduled());

        return forUpdate;
    }

    @Override
    public void makeAppointment(Appointment appointment, Pharmacy pharmacy, Long patientId) throws Exception {

        Patient pat = this.userService.findPatientById(patientId);
        if (pat == null) { throw new Exception("Patient does not exist(App service)"); }

        Appointment a = findById(appointment.getId());

        Pharmacy ph = this.pharmacyService.findById(pharmacy.getId());
        if (ph == null) { throw new Exception("Pharmacy with this id does not exist"); }

        // dodaj pregled pacijentu
        Set<Appointment> app = pat.getAppointments();
        app.add(a);
        pat.setAppointments(app);
        // dodaj pacijenta u pregled
        a.setPatient(pat);
        // dodaj pregled u apoteku
        Set<Appointment> app1 = ph.getAppointments();
        app1.add(a);
        ph.setAppointments(app1);
        // dodaj apoteku u pregled i promeni flag
        a.setPharmacy(ph);
        a.setScheduled(true);

        this.userService.updateAppointments(pat);
        this.pharmacyService.updateAppointments(ph);
        update(a);
    }

    @Override
    public List<Appointment> findScheduledByPatient(Long id) {
        List<Appointment> all= this.appointmentRepository.findScheduledByPatient(id);
        List<Appointment> activeOnes= new ArrayList<>();;

        for (Appointment app: all) {
            if (app.getStartingTime().isBefore(LocalDateTime.now())) {
                activeOnes.add(app);
            }
        }
        return activeOnes;
    }

    @Override
    public List<Appointment> findAllByPharmacy(Long phId) {
        return this.appointmentRepository.findAllByPharmacy(phId);
    }

    @Override
    public List<Appointment> findAllByPharmacyAndAdvising(Long phId, Boolean advising) {
        List<Appointment> all = this.appointmentRepository.findAllByPharmacyAndAdvising(phId, advising);
        List<Appointment> activeOnes= new ArrayList<>();;

        for (Appointment app: all) {
            if (app.getStartingTime().isAfter(LocalDateTime.now().minusHours(1))) {
                activeOnes.add(app);
            }
        }

        return activeOnes;
    }

    @Override
    public void cancelAppointment(Appointment appointment, Pharmacy pharmacy, Long patientId) throws Exception {
        Patient pat = this.userService.findPatientById(patientId);
        if (pat == null) { throw new Exception("Patient does not exist(App service)"); }

        Pharmacy ph = this.pharmacyService.findById(pharmacy.getId());
        if (ph == null) { throw new Exception("Pharmacy with this id does not exist"); }
        Appointment a = findById(appointment.getId());

        Set<Appointment> app = pat.getAppointments();
        app.remove(a);
        pat.setAppointments(app);

        Set<Appointment> app1 = ph.getAppointments();
        app1.remove(a);
        ph.setAppointments(app1);

        a.setPatient(null);
        a.setScheduled(false);

        this.userService.updateAppointments(pat);
        this.pharmacyService.updateAppointments(ph);
        update(a);
    }

    @Override
    public boolean canBeCanceled(Long appId) throws Exception {
        boolean cancel = true;
        Appointment app = findById(appId);
        LocalDateTime oneDayBeforeReservation = app.getStartingTime().minusDays(1);

        if (LocalDateTime.now().isAfter(oneDayBeforeReservation)) {
            cancel = false;
        }
        return cancel;
    }

    @Override
    public void sendEmail(Appointment ap) throws Exception {
        String to = this.userService.getPatientFromPrincipal().getEmail().toString();
        String topic = "Appointment with dermatologist";
        String body = "You successfully scheduled appointment!\n\nAppointment date and time: "
                + ap.getStartingTime().toString() + "\nDuration: " + ap.getDurationInMinutes().toString()
                + " minutes\nDermatologist: " + ap.getDermatologist().getFullName();

        this.emailService.sendEmail(to, body, topic);
    }
}
