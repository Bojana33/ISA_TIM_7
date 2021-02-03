package ftn.isa.sistemapoteka.Service.impl;

import ftn.isa.sistemapoteka.Repository.AppointmentRepository;
import ftn.isa.sistemapoteka.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }
}
