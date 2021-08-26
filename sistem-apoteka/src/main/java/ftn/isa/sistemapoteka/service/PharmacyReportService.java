package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.PharmacyReport;

import java.util.List;

public interface PharmacyReportService {
    List<PharmacyReport> calculateReport(PharmacyReport pharmacyReport);//mora biti uneta apoteka da bi se izvrsilo
}
