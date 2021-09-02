package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.dto.ChangePasswordAfterFirstLoginDTO;
import ftn.isa.sistemapoteka.model.*;

import java.util.List;

public interface UserService {
    User findById(Long id);

    User findByEmail(String email);

    List<User> findAll();

    Patient savePatient(UserRequest userRequest);

    int enableUser(String email);

    String confirmToken(String token);

    String buildEmail(String name, String link);

    PharmacyAdministrator savePharmacyAdmin(PharmacyAdministrator pharmacyAdministrator);

    Supplier saveSupplier(Supplier supplier);

    SystemAdministrator saveSystemAdmin(SystemAdministrator systemAdministrator);

    Dermatologist saveDermatologist(Dermatologist dermatologist);

    User findByEmailAndPassword(String email, String password) throws Exception;

    User changePasswordAfterFirstLogin(User user, ChangePasswordAfterFirstLoginDTO c);

    List<Dermatologist> showPharmacyDermatologists(Pharmacy pharmacy);

    List<Pharmacist> showPharmacyPharmacists(Pharmacy pharmacy);

    List<Dermatologist> filterDermatologistByRating(Integer rating, Pharmacy pharmacy);

    List<Pharmacist> filterPharmacistByRating(Integer rating, Pharmacy pharmacy);

    List<Dermatologist> findDermatologistByNameOrSurname(String name, String surname, Pharmacy pharmacy);

    List<Pharmacist> findPharmacistByNameOrSurname(String name, String surname, Pharmacy pharmacy);

    Pharmacist savePharmacist(Pharmacist pharmacist);

}
