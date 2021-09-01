package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Appointment;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.repository.AppointmentRepository;
import ftn.isa.sistemapoteka.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private UserServiceImpl userService;
    private PharmacyServiceImpl pharmacyService;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserServiceImpl userService,
                                  PharmacyServiceImpl pharmacyService) {
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
        this.pharmacyService = pharmacyService;
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
        return this.appointmentRepository.findScheduledByPatient(id);
    }

/*    @Override
    public List<Appointment> findAllScheduled() {
        return this.appointmentRepository.findAllScheduled();
    }*/

    @Override
    public List<Appointment> findAllByPharmacy(Long phId) {
        return this.appointmentRepository.findAllByPharmacy(phId);
    }

    @Override
    public List<Appointment> findAllByPharmacyAndAdvising(Long phId, Boolean advising) {
        return this.appointmentRepository.findAllByPharmacyAndAdvising(phId, advising);
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

        a.setPatient(null);

        Set<Appointment> app1 = ph.getAppointments();
        app1.remove(a);
        ph.setAppointments(app1);

        a.setScheduled(false);

        this.userService.updateAppointments(pat);
        this.pharmacyService.updateAppointments(ph);
        update(a);
    }
}
