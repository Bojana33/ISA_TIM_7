package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.dto.ChangePasswordAfterFirstLoginDTO;
import ftn.isa.sistemapoteka.email.EmailSender;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.repository.UserRepository;
import ftn.isa.sistemapoteka.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    //private PasswordEncoder passwordEncoder;

    private ConfirmationTokenServiceImpl confirmationTokenService;

    private EmailSender emailSender;

    private PharmacyServiceImpl pharmacyService;

    private LoyaltyProgramServiceImpl loyaltyProgramService;

    @Autowired
    public UserServiceImpl(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public User findByEmail(String email) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(email);
        return u;
    }

    public User findById(Long id) throws AccessDeniedException {
        return userRepository.findById(id).orElseGet(null);
    }

    public List<User> findAll() throws AccessDeniedException {
        return userRepository.findAll();
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
        emailSender.send(userRequest.getEmail(), buildEmail(userRequest.getFirstName(), link));
        return u;
    }

    public void defineUserCategory(Patient patient) {
        LoyaltyProgram loyaltyProgram = this.loyaltyProgramService.getLP(1L);
        if (patient.getLoyaltyPoints() == 0.0) {
            patient.setUserCategory(UserCategory.NONE);
        } else if (patient.getLoyaltyPoints() >= loyaltyProgram.getRegularPoints() && patient.getLoyaltyPoints() < loyaltyProgram.getSilverPoints()) {
            patient.setUserCategory(UserCategory.REGULAR);
        } else if (patient.getLoyaltyPoints() >= loyaltyProgram.getSilverPoints() && patient.getLoyaltyPoints() < loyaltyProgram.getGoldPoints()) {
            patient.setUserCategory(UserCategory.SILVER);
        } else if (patient.getLoyaltyPoints() >= loyaltyProgram.getGoldPoints()) {
            patient.setUserCategory(UserCategory.GOLD);
        } else {
            patient.setUserCategory(UserCategory.NONE);
        }
        this.userRepository.save(patient);

    }

    public Integer discount(Patient patient) {
        LoyaltyProgram loyaltyProgram = this.loyaltyProgramService.getLP(1L);
        if (patient.getUserCategory() == UserCategory.GOLD) {
            return loyaltyProgram.getDiscountGold();
        }
        if (patient.getUserCategory() == UserCategory.SILVER) {
            return loyaltyProgram.getDiscountSilver();
        }
        if (patient.getUserCategory() == UserCategory.REGULAR) {
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
    public List<Dermatologist> showPharmacyDermatologists(Pharmacy pharmacy) {

        return (List<Dermatologist>) pharmacy.getDermatologists();

    }

    @Override
    public List<Pharmacist> showPharmacyPharmacists(Pharmacy pharmacy) {

        return (List<Pharmacist>) pharmacy.getPharmacists();
    }

    @Override
    public List<Dermatologist> filterDermatologistByRating(Integer rating, Pharmacy pharmacy) {
        List<Dermatologist> dermatologists = showPharmacyDermatologists(pharmacy);
        List<Dermatologist> ret = new ArrayList<>();
        Double r = Double.valueOf(rating);
        for (Dermatologist d :
                dermatologists) {
            if (r + 0.5 > d.getAverageRating() && r - 0.5 < d.getAverageRating()) {
                ret.add(d);
            }
        }
        return ret;
    }

    @Override
    public List<Pharmacist> filterPharmacistByRating(Integer rating, Pharmacy pharmacy) {
        List<Pharmacist> pharmacist = showPharmacyPharmacists(pharmacy);
        List<Pharmacist> ret = new ArrayList<>();
        Double r = Double.valueOf(rating);
        for (Pharmacist d :
                pharmacist) {
            if (r + 0.5 > d.getAverageRating() && r - 0.5 < d.getAverageRating()) {
                ret.add(d);
            }
        }
        return ret;
    }

    @Override
    public List<Dermatologist> findDermatologistByNameOrSurname(String name, String surname, Pharmacy pharmacy) {
        List<Dermatologist> dermatologists = showPharmacyDermatologists(pharmacy);
        List<Dermatologist> matches = this.userRepository.findDermatologistsByFirstNameContainingAndLastNameContaining(name, surname);
        List<Dermatologist> ret = new ArrayList<>();
        for (Dermatologist d :
                dermatologists) {
            for (Dermatologist m :
                    matches) {
                if (d.getId().equals(m.getId())) {
                    ret.add(d);
                }
            }
        }
        return ret;
    }

    @Override
    public List<Pharmacist> findPharmacistByNameOrSurname(String name, String surname, Pharmacy pharmacy) {
        List<Pharmacist> pharmacists = showPharmacyPharmacists(pharmacy);
        List<Pharmacist> matches = this.userRepository.findPharmacistByFirstNameContainingAndLastNameContaining(name, surname);
        List<Pharmacist> ret = new ArrayList<>();
        for (Pharmacist d :
                pharmacists) {
            for (Pharmacist m :
                    matches) {
                if (d.getId().equals(m.getId())) {
                    ret.add(d);
                }
            }
        }
        return ret;
    }


    @Override
    public Pharmacist savePharmacist(Pharmacist pharmacist) {
        pharmacist.setEnabled(true);
        pharmacist.setPassword(pharmacist.getPassword());
        pharmacist.setUserRole(UserRole.PHARMACIST);

        this.userRepository.save(pharmacist);
        return pharmacist;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws Exception {
        User user = this.userRepository.findByEmailAndPassword(email, password);
        if (user == null) {
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


}
