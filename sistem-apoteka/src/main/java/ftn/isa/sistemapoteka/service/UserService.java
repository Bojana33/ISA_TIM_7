package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.*;

import java.util.List;

public interface UserService {
    User findById(Long id);
    User findByEmail(String email);
    List<User> findAll ();
    Patient savePatient(UserRequest userRequest);
    int enableUser(String email);
    String confirmToken(String token);
    String buildEmail(String name, String link);
    PharmacyAdministrator savePharmacyAdmin(PharmacyAdministrator pharmacyAdministrator, Long pharmId);
    Supplier saveSupplier(Supplier supplier);
    SystemAdministrator saveSystemAdmin(SystemAdministrator systemAdministrator);
    Dermatologist saveDermatologist(Dermatologist dermatologist);

    Pharmacist savePharmacist(Pharmacist pharmacist);
}
