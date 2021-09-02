package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Appointment;
import ftn.isa.sistemapoteka.repository.AppointmentRepository;
import ftn.isa.sistemapoteka.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return this.appointmentRepository.save(appointment);
    }
}
