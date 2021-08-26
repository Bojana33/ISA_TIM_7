package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.dto.ChangePasswordAfterFirstLoginDTO;
import ftn.isa.sistemapoteka.model.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User findById(Long id);
    User findByEmail(String email);
    List<User> findAll ();
    Patient findPatientById(Long id) throws Exception;
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

    Patient updatePatientProfile(Patient patient) throws Exception;
    Page<Patient> findPaginatedPatientDrugs(int pageNum, int pageSize);
    Patient addAllergyTrigger(Patient patient, Drug drug) throws Exception;
    Patient savePatient(Patient patient) throws Exception;
}
