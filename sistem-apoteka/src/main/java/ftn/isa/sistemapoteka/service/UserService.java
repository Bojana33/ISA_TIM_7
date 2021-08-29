package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.dto.ChangePasswordAfterFirstLoginDTO;
import ftn.isa.sistemapoteka.model.*;

import java.util.List;
import java.util.Set;

public interface UserService {
    User findById(Long id);
    User findByEmail(String email);
    List<User> findAll ();
    Patient savePatient(UserRequest userRequest);
    int enableUser(String email);
    String confirmToken(String token);
    String buildEmail(String name, String link);
    PharmacyAdministrator savePharmacyAdmin(PharmacyAdministrator pharmacyAdministrator);
    Supplier saveSupplier(Supplier supplier);
    SystemAdministrator saveSystemAdmin(SystemAdministrator systemAdministrator);
    Dermatologist saveDermatologist(Dermatologist dermatologist);
    User findByEmailAndPassword(String email, String password);
    User changePasswordAfterFirstLogin(User user, ChangePasswordAfterFirstLoginDTO c);
    User updateProfile(User user);

    void subscribePatient(Patient patient, Pharmacy pharmacy) throws Exception;

    void unsubscribePatient(Patient patient, Pharmacy pharmacy);

    Set<Pharmacy> findAllSubscribedPharmacies(Patient patient);
}
