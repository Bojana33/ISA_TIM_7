package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Days;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.model.PharmacyAdministrator;

import java.util.List;

public interface DaysService {
    Days saveDays(Days days);

    List<Days> getPharmacistVacationRequests(Pharmacy pharmacy);

    List<Days> getDermatologistVacationRequests(Pharmacy pharmacy);

    List<Days> getDermatologistFreeDays(Pharmacy pharmacy);

    String buildEmail(String reason, Days days);

    Days reviewVacation(Days days, PharmacyAdministrator pharmacyAdministrator, String reason);
}
