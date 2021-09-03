package ftn.isa.sistemapoteka.controller;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
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

    @Autowired
    public eRecipeController(eRecipeService eRecipeService, UserServiceImpl userService, PharmacyServiceImpl pharmacyService, DrugServiceImpl drugService){
        this.eRecipeService = eRecipeService;
        this.userService = userService;
        this.pharmacyService = pharmacyService;
        this.drugService = drugService;
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
        this.pharmacyService.update(pharmacy);
        return new ModelAndView("redirect:/user/patient/home");
    }

//    @RequestMapping("/sortByName/{drugCodes}")
//    @PreAuthorize("hasRole('PATIENT')")
//    public ModelAndView sortByName(Model model, @PathVariable String drugCodes){
//        String codes[] = drugCodes.split(",");
//        List<Long> codesLong = new ArrayList<>();
//        for (String code: codes){
//            Long idLong = Long.valueOf(code);
//            codesLong.add(idLong);
//        }
//        List<Pharmacy> pharmacies = this.pharmacyService.sortByNameAsc();
//        Map<Pharmacy,Double> pharmaciesPrice = this.eRecipeService.calculatePrice(pharmacies,codesLong);
//        model.addAttribute("pharmacies", pharmaciesPrice);
//        return new ModelAndView("pharmacies-recipe");
//    }



}
