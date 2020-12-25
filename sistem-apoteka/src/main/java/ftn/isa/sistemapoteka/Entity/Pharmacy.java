package ftn.isa.sistemapoteka.Entity;

import java.util.Set;

public class Pharmacy {
    private Integer id;
    private String name;
    private String address;
    private String description;
    private Set<Appointment> freeDermatologistAppointments;
    private Set<Dermatolgist> dermatologists;
    private Set<Pharmacist> pharmacists;
    private Set<Drug> drugs;
    private Double avgRating;
    private Set<Discount> discounts;

}