package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Days;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.model.PharmacyAdministrator;

import java.util.List;

public interface DaysService {
    Days saveDays(Days days);

    List<Days> getPharmacistVacationRequests(PharmacyAdministrator pharmacyAdministrator, Pharmacy pharmacy);

    List<Days> getDermatologistVacationRequests(PharmacyAdministrator pharmacyAdministrator, Pharmacy pharmacy);

    String buildEmail(String reason, Days days);

    Days reviewVacation(Days days, PharmacyAdministrator pharmacyAdministrator, String reason);
}
