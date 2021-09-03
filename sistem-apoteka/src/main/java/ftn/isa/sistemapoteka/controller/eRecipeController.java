package ftn.isa.sistemapoteka.controller;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import ftn.isa.sistemapoteka.email.EmailSender;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.qrCode.QrCode;
import ftn.isa.sistemapoteka.service.impl.DrugServiceImpl;
import ftn.isa.sistemapoteka.service.impl.PharmacyServiceImpl;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ftn.isa.sistemapoteka.service.eRecipeService;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("eRecipes")
public class eRecipeController {

    private eRecipeService eRecipeService;
    private UserServiceImpl userService;
    private PharmacyServiceImpl pharmacyService;
    private DrugServiceImpl drugService;
    private EmailSender emailSender;

    @Autowired
    public eRecipeController(eRecipeService eRecipeService, UserServiceImpl userService, PharmacyServiceImpl pharmacyService, DrugServiceImpl drugService, EmailSender emailSender){
        this.eRecipeService = eRecipeService;
        this.userService = userService;
        this.pharmacyService = pharmacyService;
        this.drugService = drugService;
        this.emailSender = emailSender;
    }


    @GetMapping("/uploadQR/{id}")
    public ModelAndView uploadQRForm(Model model, @PathVariable Long id) throws WriterException, IOException{
        eRecipe eRecipe = new eRecipe();
        model.addAttribute("eRecipe", eRecipe);
        model.addAttribute(id);
        Patient patient = (Patient) userService.findById(id);
        String data = new String();
        for (DrugReservation drugReservation:  patient.getDrugReservations()){
            data += drugReservation.getDrug().getCode().toString() + ',';
        }
        System.out.println(data);
        int number = patient.getERecipes().size() + 1;
        System.out.println(data);
        Path path = Paths.get("E:\\Internet_Softverske_Arhitekture\\projekat\\Git\\sistem-apoteka\\src\\main\\resources\\static\\qr-codes\\QrCode"+ id + number+".png");
        String charset = "UTF-8";
        model.addAttribute(path);
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QrCode.generateQRcode(data,path,charset,hashMap,200,200);
        return new ModelAndView("upload-qr");
    }

    @GetMapping("/readQR/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView readQR(Model model, @PathVariable Long id, @ModelAttribute eRecipe eRecipe) throws FileNotFoundException, IOException, NotFoundException {
        Patient patient = (Patient) this.userService.findById(id);
        int number = patient.getERecipes().size() + 1;
        Path path = Paths.get("E:\\Internet_Softverske_Arhitekture\\projekat\\Git\\sistem-apoteka\\src\\main\\resources\\static\\qr-codes\\QrCode"+ id + number+".png");
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        String drugCodes = QrCode.readQRcode(path.toString(),charset,hashMap);
        String codes[] = drugCodes.split(",");
        List<Long> codesLong = new ArrayList<>();
        for (String code: codes){
            Long idLong = Long.valueOf(code);
            codesLong.add(idLong);
        }
//        List<Drug> drugs = new ArrayList<>();
//        for (Long code: codesLong){
//            Drug drug = this.drugService.findByCode(code);
//            drugs.add(drug);
//        }
        List<Pharmacy> pharmacies = this.pharmacyService.findByPatientDrugs(codesLong);
        Map<Pharmacy,Double> pharmaciesPrice = this.eRecipeService.calculatePrice(pharmacies,codesLong);
        model.addAttribute("pharmacies", pharmaciesPrice);
        model.addAttribute("drugCodes",drugCodes);
        return new ModelAndView("pharmacies-recipe");

    }

    public String emaileRecipe(String name, String text, String pharmacyName) {
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:10px;line-height:25px;color:#0b0c0c\"> <p>" + text + pharmacyName +"</p> </p></blockquote>\n" +
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

    @PostMapping("/buyDrugs/{id}/{drugCodes}")
    @PreAuthorize("hasRole('PATIENT')")
    public ModelAndView buyDrugs(@PathVariable Long id, @PathVariable String drugCodes, Authentication authentication){
        String codes[] = drugCodes.split(",");
        List<Long> codesLong = new ArrayList<>();
        for (String code: codes){
            Long idLong = Long.valueOf(code);
            codesLong.add(idLong);
        }
        List<Drug> drugs = new ArrayList<>();
        for (Long code: codesLong){
            Drug drug = this.drugService.findByCode(code);
            drugs.add(drug);
        }
        eRecipe eRecipe = new eRecipe();
        Patient patient = (Patient) this.userService.findByEmail(authentication.getName());
        Pharmacy pharmacy = this.pharmacyService.findById(id);
        eRecipe.setPatient(patient);
        eRecipe.setDrugs(drugs);
        eRecipe.setDateOfIssue(LocalDateTime.now());
        eRecipe.setPharmacy(pharmacy);
        this.eRecipeService.save(eRecipe);
        for (Map.Entry<Long, Integer> map: pharmacy.getDrugsQuantity().entrySet()){
            for(Drug drug:drugs){
                if (map.getKey().equals(drug.getCode())){
                    map.setValue(map.getValue()-1);
                }
            }
        }
        emailSender.send(patient.getEmail(),emaileRecipe(patient.getFirstName(),"Your eRecipe has been issued in pharmacy ",pharmacy.getName()));
        this.pharmacyService.update(pharmacy);
        return new ModelAndView("redirect:/user/patient/home");
    }



}
