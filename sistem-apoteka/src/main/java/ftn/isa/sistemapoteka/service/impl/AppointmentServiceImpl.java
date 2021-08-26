package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Appointment;
import ftn.isa.sistemapoteka.repository.AppointmentRepository;
import ftn.isa.sistemapoteka.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
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
}
