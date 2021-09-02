package ftn.isa.sistemapoteka.service.impl;


import ftn.isa.sistemapoteka.email.EmailSender;
import ftn.isa.sistemapoteka.model.Days;
import ftn.isa.sistemapoteka.model.Dermatologist;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.model.PharmacyAdministrator;
import ftn.isa.sistemapoteka.repository.DaysRepository;
import ftn.isa.sistemapoteka.service.DaysService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class DaysServiceImpl implements DaysService {
    private final DaysRepository daysRepository;

    private EmailSender emailSender;

    @Autowired
    public DaysServiceImpl(DaysRepository daysRepository) {
        this.daysRepository = daysRepository;
    }


    @Override
    public Days saveDays(Days days) {
        this.daysRepository.save(days);
        return days;
    }

    @Override
    public List<Days> getPharmacistVacationRequests(Pharmacy pharmacy) {

        return this.daysRepository.findDaysByVacationReviewedIsNullAndPharmacist_Pharmacy(pharmacy);
    }

    @Override
    public List<Days> getDermatologistVacationRequests(Pharmacy pharmacy) {
        Set<Dermatologist> dermatologists = pharmacy.getDermatologists();
        return this.daysRepository.findDaysByVacationReviewedIsNullAndDermatologist(dermatologists);
    }

    @Override
    public List<Days> getDermatologistFreeDays(Pharmacy pharmacy) {
        List<Dermatologist> dermatologists = (List<Dermatologist>) pharmacy.getDermatologists();
        List<Days> days = new ArrayList<>();
        for (Dermatologist d :
                dermatologists) {
            List<Days> temp = this.daysRepository.findDaysByDermatologistAndAppointedIsFalseAndVacationSubmittedIsFalse(d);
            for (Days days1 :
                    temp) {
                days.add(days1);
            }
        }
        return days;
    }

    @Override
    public String buildEmail(String reason, Days days) {
        String approval;
        if (days.isVacationApproved()) {
            approval = "been approved";
        } else {
            approval = "not been approved. ";
            approval.concat(reason);
        }
        return "<div> \n" +
                "<p style=\"margin: 0 0 20px 0; font-size: 19px; line-height: 25px; color: #0b0c0c\"> \n" +
                "Your reqest for vacation has " + approval + ". </p>\n" +
                "</div>";
    }


    @Override
    public Days reviewVacation(Days days, PharmacyAdministrator pharmacyAdministrator, String reason) {
        days.setVacationReviewed(true);
        if (days.getPharmacist() == null) {
            emailSender.send(days.getDermatologist().getEmail(), buildEmail(reason, days));
        } else {
            emailSender.send(days.getPharmacist().getEmail(), buildEmail(reason, days));
        }
        return days;
    }


}
