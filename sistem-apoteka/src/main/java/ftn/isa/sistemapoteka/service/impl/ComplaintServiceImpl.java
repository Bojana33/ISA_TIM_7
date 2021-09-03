package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.email.EmailSender;
import ftn.isa.sistemapoteka.model.Complaint;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.repository.ComplaintRepository;
import ftn.isa.sistemapoteka.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private ComplaintRepository complaintRepository;
    private EmailSender emailSender;
    private UserServiceImpl userService;

    @Autowired
    public ComplaintServiceImpl(ComplaintRepository complaintRepository, EmailSender emailSender, UserServiceImpl userService){
        this.complaintRepository = complaintRepository;
        this.emailSender = emailSender;
        this.userService = userService;
    }

    @Override
    public List<Complaint> findAllComplaints() {
        return this.complaintRepository.findAll();
    }

    @Override
    public Complaint saveComplaint(Complaint complaint) {
        Complaint c = new Complaint();
        c.setText(complaint.getText());
        c.setPatient(complaint.getPatient());
        c.setComplaintType(complaint.getComplaintType());
        c.setPharmacyId(complaint.getPharmacyId());
        c.setIsAnswered(complaint.getIsAnswered());
        c.setResponse(complaint.getResponse());
        c.setPharmacistId(complaint.getPharmacistId());
        c.setDermatologistId(complaint.getDermatologistId());
        return this.complaintRepository.save(c);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Complaint updateComplaint(Complaint complaint) {
        Complaint c = this.complaintRepository.getOne(complaint.getId());
        c.setText(complaint.getText());
        c.setPatient(complaint.getPatient());
        c.setComplaintType(complaint.getComplaintType());
        c.setPharmacyId(complaint.getPharmacyId());
        c.setIsAnswered(complaint.getIsAnswered());
        c.setResponse(complaint.getResponse());
        c.setPharmacistId(complaint.getPharmacistId());
        c.setDermatologistId(complaint.getDermatologistId());
        return this.complaintRepository.save(c);
    }

    @Override
    public Complaint findById(Long id) throws Exception {
        if (this.complaintRepository.getOne(id) == null){
            throw new Exception("Complaint with this id doesn't exist");
        }
        return this.complaintRepository.getOne(id);
    }

    public String emailResponseToComplaint(String name, String text) {
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
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Answer to complaint:</span>\n" +
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:10px;line-height:25px;color:#0b0c0c\"> <p>" + text + "</p> </p></blockquote>\n" +
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
    public void sendResponse(Complaint complaint) {
        Patient patient = (Patient) this.userService.findById(complaint.getPatient().getId());
        emailSender.send(patient.getEmail(),emailResponseToComplaint(patient.getFirstName(),complaint.getResponse()));
    }
}
