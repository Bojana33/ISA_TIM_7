package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.dto.ChangePasswordAfterFirstLoginDTO;
import ftn.isa.sistemapoteka.email.EmailSender;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.repository.PatientRepository;
import ftn.isa.sistemapoteka.repository.UserRepository;
import ftn.isa.sistemapoteka.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PatientRepository patientRepository;

    //private PasswordEncoder passwordEncoder;

    private ConfirmationTokenServiceImpl confirmationTokenService;

    private EmailSender emailSender;

    private PharmacyServiceImpl pharmacyService;

    private DrugServiceImpl drugService;

    private LoyaltyProgramServiceImpl loyaltyProgramService;

    @Override
    public User findByEmail(String email) {
        User u = userRepository.findByEmail(email);
        return u;
    }

    public User findById(Long id) throws AccessDeniedException {
        User u = userRepository.findById(id).orElseGet(null);
        return u;
    }

    public List<User> findAll() throws AccessDeniedException {
        List<User> result = userRepository.findAll();
        return result;
    }

    public Patient findPatientById(Long id) throws Exception{
        if (!this.patientRepository.findById(id).isPresent()) {
            throw new Exception("No such value(Patient service)");
        }
        Patient patient = this.patientRepository.findById(id).get();
        if (patient == null) {
            throw new Exception("Patient with this id does not exist");
        }
        return patient;
    }

    @Override
    public Patient savePatient(UserRequest userRequest) {
        LoyaltyProgram loyaltyProgram = this.loyaltyProgramService.getLP(1L);
        Patient u = new Patient();
        u.setEmail(userRequest.getEmail());
        u.setPassword(userRequest.getPassword());
        u.setFirstName(userRequest.getFirstName());
        u.setLastName(userRequest.getLastName());
        u.setCity(userRequest.getCity());
        u.setResidence(userRequest.getResidence());
        u.setState(userRequest.getState());
        u.setPhoneNumber(userRequest.getPhoneNumber());
        u.setEnabled(false); //setujemo na true kada korisnik potvrdi registraciju preko emaila

        u.setUserRole(UserRole.PATIENT);

        u = this.userRepository.save(u);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                u
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:8081/myPharmacy/auth/confirm?token=" + token;
        emailSender.send(userRequest.getEmail(),buildEmail(userRequest.getFirstName(),link));
        return u;
    }

    public UserCategory defineUserCategory(Patient patient,LoyaltyProgram loyaltyProgram){
        if (patient.getLoyaltyPoints() >= loyaltyProgram.getRegularPoints() && patient.getLoyaltyPoints() < loyaltyProgram.getSilverPoints()){
            return UserCategory.REGULAR;
        }
        if (patient.getLoyaltyPoints() >= loyaltyProgram.getSilverPoints() && patient.getLoyaltyPoints() < loyaltyProgram.getGoldPoints()){
            return UserCategory.SILVER;
        }
        if (patient.getLoyaltyPoints() >=  loyaltyProgram.getGoldPoints()){
            return UserCategory.GOLD;
        }
        return null;
    }

    public Integer discount(Patient patient, LoyaltyProgram loyaltyProgram){
        if (patient.getUserCategory() == UserCategory.GOLD){
            return loyaltyProgram.getDiscountGold();
        }
        if (patient.getUserCategory() == UserCategory.SILVER){
            return loyaltyProgram.getDiscountSilver();
        }
        if (patient.getUserCategory() == UserCategory.REGULAR){
            return loyaltyProgram.getDiscountRegular();
        }
        return -1;
    }

    @Override
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    @Override
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userRepository.enableUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    public String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    @Override
    public PharmacyAdministrator savePharmacyAdmin(PharmacyAdministrator pharmacyAdministrator) {
        pharmacyAdministrator.setEnabled(true);
        pharmacyAdministrator.setPassword(pharmacyAdministrator.getPassword());
        pharmacyAdministrator.setIsFirstLogin(true);

        pharmacyAdministrator.setUserRole(UserRole.PHARMACY_ADMIN);

        this.userRepository.save(pharmacyAdministrator);
        return pharmacyAdministrator;
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        supplier.setEnabled(true);
        supplier.setPassword(supplier.getPassword());
        supplier.setIsFirstLogin(true);

        supplier.setUserRole(UserRole.SUPPLIER);

        this.userRepository.save(supplier);
        return supplier;
    }

    @Override
    public SystemAdministrator saveSystemAdmin(SystemAdministrator systemAdministrator) {
        systemAdministrator.setEnabled(true);
        systemAdministrator.setPassword(systemAdministrator.getPassword());
        systemAdministrator.setIsFirstLogin(true);

        systemAdministrator.setUserRole(UserRole.SYS_ADMIN);

        this.userRepository.save(systemAdministrator);
        return systemAdministrator;
    }

    @Override
    public Dermatologist saveDermatologist(Dermatologist dermatologist) {
        dermatologist.setEnabled(true);
        dermatologist.setPassword(dermatologist.getPassword());
        dermatologist.setIsFirstLogin(true);

        dermatologist.setUserRole(UserRole.DERMATOLOGIST);

        this.userRepository.save(dermatologist);
        return dermatologist;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws Exception {
        User user = this.userRepository.findByEmailAndPassword(email,password);
        if (user==null){
            throw new Exception("User with this credentials doesn't exist.");
        }
        return user;
    }

    @Override
    public User changePasswordAfterFirstLogin(User user, ChangePasswordAfterFirstLoginDTO c) {
        user.setPassword(c.getNewPassword());
        this.userRepository.save(user);
        return user;
    }

    @Override
    public Patient updatePatientProfile(Patient patient) throws Exception{
        Patient forUpdate = this.findPatientById(patient.getId());
        if (forUpdate == null) { throw new Exception("Patient does not exist"); }

        forUpdate.setFirstName(patient.getFirstName());
        forUpdate.setLastName(patient.getLastName());
        forUpdate.setResidence(patient.getResidence());
        forUpdate.setCity(patient.getCity());
        forUpdate.setState(patient.getState());
        forUpdate.setPhoneNumber(patient.getPhoneNumber());

        this.patientRepository.save(forUpdate);
        return forUpdate;
    }

    @Override
    public Page<Patient> findPaginatedPatientDrugs(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        return this.patientRepository.findAll(pageable);
    }

    @Override
    public Patient addAllergyTrigger(Patient patient, Drug drug) throws Exception{
        Patient p = this.patientRepository.findById(patient.getId()).get();
        if(p == null) { throw new Exception("Patient with this id does not exist"); }
        Drug d = this.drugService.findByName(drug.getName()).get(0);
        if(d == null) { throw new Exception("drug with this code does not exist"); }

        Set<Drug> triggers = p.getAllergyTriggers();
        triggers.add(d);
        p.setAllergyTriggers(triggers);

        Set<Patient> patientsWithAllergies = d.getPatientsWithAllergies();
        patientsWithAllergies.add(p);
        d.setPatientsWithAllergies(patientsWithAllergies);

        savePatient(p);
        this.drugService.saveDrug(d);

        return p;
    }

    @Override
    public Patient savePatient(Patient patient) throws Exception {
        Patient p = this.patientRepository.findById(patient.getId()).get();
        if(p == null) { throw new Exception("Patient with this id does not exist"); }

        return this.userRepository.save(p);
    }
}
