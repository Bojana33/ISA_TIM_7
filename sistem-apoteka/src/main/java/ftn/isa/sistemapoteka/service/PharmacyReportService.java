package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.model.PharmacyReport;

public interface PharmacyReportService {
    PharmacyReport calculateReport(Pharmacy pharmacy);//mora biti uneta apoteka da bi se izvrsilo
}
