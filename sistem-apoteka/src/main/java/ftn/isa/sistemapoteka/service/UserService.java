package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.dto.ChangePasswordAfterFirstLoginDTO;
import ftn.isa.sistemapoteka.model.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    User findById(Long id);
    User findByEmail(String email);
    List<User> findAll ();
    Patient findPatientById(Long id) throws Exception;
    Patient findPatientByEmail(String email) throws Exception;
    Patient savePatient(UserRequest userRequest);
    int enableUser(String email);
    void sendEmail(String to, String body, String topic);
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
    Patient updateAppointments(Patient patient) throws Exception;
    Patient removeAllergyTrigger(Patient patient, Drug drug) throws Exception;
    Patient getPatientFromPrincipal() throws Exception;

    List<Pharmacist> findAvailablePharmacist(Long phId, LocalDateTime ldt) throws Exception;

    Pharmacist findPharmacistById(Long phId) throws Exception;
}
