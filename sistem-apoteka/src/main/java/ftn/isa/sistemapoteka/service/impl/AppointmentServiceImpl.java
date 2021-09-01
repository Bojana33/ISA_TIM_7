package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Appointment;
import ftn.isa.sistemapoteka.model.Consultation;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.repository.AppointmentRepository;
import ftn.isa.sistemapoteka.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Appointment> findAllByPatient(Patient patient) {
        return this.appointmentRepository.findAllByPatient(patient);
    }
}
