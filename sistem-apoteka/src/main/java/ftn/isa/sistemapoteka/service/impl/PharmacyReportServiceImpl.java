package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.model.PharmacyReport;
import ftn.isa.sistemapoteka.repository.AppointmentRepository;
import ftn.isa.sistemapoteka.repository.PharmacyReportRepository;
import ftn.isa.sistemapoteka.service.PharmacyReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PharmacyReportServiceImpl implements PharmacyReportService {
    private final PharmacyReportRepository pharmacyReportRepository;
    private AppointmentRepository appointmentRepository;

    public PharmacyReportServiceImpl(PharmacyReportRepository pharmacyReportRepository) {
        this.pharmacyReportRepository = pharmacyReportRepository;
    }

    @Override
    public List<PharmacyReport> calculateReport(PharmacyReport pharmacyReport) {
        Pharmacy pharmacy = pharmacyReport.getPharmacy();

        return null;
    }
}
