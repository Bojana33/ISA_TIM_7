package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment findById(Long id) throws Exception;
    List<Appointment> findAll();
}
