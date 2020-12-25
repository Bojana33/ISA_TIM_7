package ftn.isa.sistemapoteka.Entity;

import java.util.List;

public class Pharmacy {
    private Integer id;
    private String name;
    private String address;
    private String description;
    private List<Appointment> freeDermatologistAppointments;
    private List<Dermatolgist> dermatologists;
    private List<Pharmacist> pharmacists;
    private List<Drug> drugs;


}