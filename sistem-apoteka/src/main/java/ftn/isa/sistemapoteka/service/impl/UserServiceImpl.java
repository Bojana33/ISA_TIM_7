package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.dto.ChangePasswordAfterFirstLoginDTO;
import ftn.isa.sistemapoteka.email.EmailSender;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.repository.DermatologistRepository;
import ftn.isa.sistemapoteka.repository.PharmacistRepository;
import ftn.isa.sistemapoteka.repository.PharmacyRepository;
import ftn.isa.sistemapoteka.repository.UserRepository;
import ftn.isa.sistemapoteka.service.PharmacyService;
import ftn.isa.sistemapoteka.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.Console;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ConfirmationTokenServiceImpl confirmationTokenService;

    private EmailSender emailSender;

    private PharmacyServiceImpl pharmacyService;

    private LoyaltyProgramServiceImpl loyaltyProgramService;

    private PharmacyRepository pharmacyRepository;

    private PharmacistRepository pharmacistRepository;

    private DermatologistRepository dermatologistRepository;

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

    public void defineUserCategory(Patient patient){
        LoyaltyProgram loyaltyProgram= this.loyaltyProgramService.getLP(1L);
        if (patient.getLoyaltyPoints()==0.0){
            patient.setUserCategory(UserCategory.NONE);
        }
        else if (patient.getLoyaltyPoints() >= loyaltyProgram.getRegularPoints() && patient.getLoyaltyPoints() < loyaltyProgram.getSilverPoints()){
            patient.setUserCategory(UserCategory.REGULAR);
        }
        else if (patient.getLoyaltyPoints() >= loyaltyProgram.getSilverPoints() && patient.getLoyaltyPoints() < loyaltyProgram.getGoldPoints()){
            patient.setUserCategory(UserCategory.SILVER);
        }
        else if (patient.getLoyaltyPoints() >= loyaltyProgram.getGoldPoints()){
            patient.setUserCategory(UserCategory.GOLD);
        }
        else {
            patient.setUserCategory(UserCategory.NONE);
        }
        this.userRepository.save(patient);

    }

    public Integer discount(Patient patient){
        LoyaltyProgram loyaltyProgram = this.loyaltyProgramService.getLP(1L);
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
    public User findByEmailAndPassword(String email, String password) {
        User user = this.userRepository.findByEmailAndPassword(email,password);
        return user;
    }

    @Override
    public User changePasswordAfterFirstLogin(User user, ChangePasswordAfterFirstLoginDTO c) {
        user.setPassword(c.getNewPassword());
        this.userRepository.save(user);
        return user;
    }

    @Override
    public User updateProfile(User user) {
        User u = this.userRepository.getOne(user.getId());
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setCity(user.getCity());
        u.setResidence(user.getResidence());
        u.setState(user.getState());
        u.setPhoneNumber(user.getPhoneNumber());
        return this.userRepository.save(u);
    }

    @Override
    public void subscribePatient(Patient patient, Pharmacy pharmacy) throws Exception {
        if (patient.getSubscriptions().contains(pharmacy)){
            throw new Exception("You are already subscribed to this pharmacy");
        }

        Set<Patient> subscribePatients = pharmacy.getSubscriptionedPatients();
        subscribePatients.add(patient);
        pharmacy.setSubscriptionedPatients(subscribePatients);
        this.pharmacyRepository.save(pharmacy);

        Set<Pharmacy> subscriptions = patient.getSubscriptions();
        subscriptions.add(pharmacy);
        patient.setSubscriptions(subscriptions);
        this.userRepository.save(patient);
    }

    @Override
    public void unsubscribePatient(Patient patient, Pharmacy pharmacy){
        Set<Pharmacy> subscriptions = patient.getSubscriptions();
        subscriptions.remove(pharmacy);
        patient.setSubscriptions(subscriptions);
        this.userRepository.save(patient);

        Set<Patient> subscribePatients = pharmacy.getSubscriptionedPatients();
        subscribePatients.remove(patient);
        pharmacy.setSubscriptionedPatients(subscribePatients);
        this.pharmacyRepository.save(pharmacy);
    }

    @Override
    public Set<Pharmacy> findAllSubscribedPharmacies(Patient patient) {
        return this.pharmacyRepository.findAllBySubscriptionedPatients(patient);
    }

    @Override
    public List<User> findDermatologists() {
        return this.userRepository.findAllByUserRole(UserRole.DERMATOLOGIST);
    }

    @Override
    public List<User> findPharmacists() {
        return this.userRepository.findAllByUserRole(UserRole.PHARMACIST);
    }

    @Override
    public User findPharmacistByConsultations(Consultation consultation) {
        return this.pharmacistRepository.findByConsultations(consultation);
    }
    @Override
    public List<User> findPharmacistsByConsultations(List<Consultation> consultations) {
        List<User> pharmacists = new ArrayList<>();
        for (Consultation consultation : consultations) {
            User user = findPharmacistByConsultations(consultation);
            pharmacists.add(user);
        }
        return pharmacists;
    }

    @Override
    public User findDermatologistByAppointments(Appointment appointment) {
        return this.dermatologistRepository.findByAppointments(appointment);
    }

    @Override
    public List<User> findDermatologistsByAppointments(List<Appointment> appointments) {
        List<User> dermatologists = new ArrayList<>();
        for (Appointment appointment : appointments) {
            User user = findDermatologistByAppointments(appointment);
            dermatologists.add(user);
        }
        return dermatologists;
    }


}
