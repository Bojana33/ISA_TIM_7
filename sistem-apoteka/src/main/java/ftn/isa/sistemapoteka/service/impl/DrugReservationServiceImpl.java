package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.email.EmailService;
import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.DrugReservation;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.repository.DrugRepository;
import ftn.isa.sistemapoteka.repository.DrugReservationRepository;
import ftn.isa.sistemapoteka.service.DrugReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class DrugReservationServiceImpl implements DrugReservationService {

    private DrugReservationRepository drugReservationRepository;
    private DrugRepository drugRepository;
    public EmailService emailService;
    public UserServiceImpl userService;

    @Autowired
    public DrugReservationServiceImpl(DrugReservationRepository drugReservationRepository,
                                      DrugRepository drugRepository, EmailService emailService,
                                      UserServiceImpl userService) {
        this.drugReservationRepository = drugReservationRepository;
        this.drugRepository = drugRepository;
        this.emailService = emailService;
        this.userService = userService;
    }

    @Override
    public DrugReservation findById(Long id) throws Exception {
        if (!this.drugReservationRepository.findById(id).isPresent()) {
            throw new Exception("No such value(Drug reservation service)");
        }

        return this.drugReservationRepository.findById(id).get();
    }

    @Override
    public List<DrugReservation> findAll() {
        return this.drugReservationRepository.findAll();
    }

    @Override
    public DrugReservation save(DrugReservation drugReservation) throws Exception {
        if (this.drugReservationRepository.findById(drugReservation.getId()).isPresent()) {
            throw new Exception("Value already exist(Drug reservation service-save)");
        }

        return this.drugReservationRepository.save(drugReservation);
    }

    @Override
    public void deleteById(Long id) {
        this.drugReservationRepository.deleteById(id);
    }

    @Override
    public DrugReservation saveDR(DrugReservation dr) {
        return this.drugReservationRepository.save(dr);
    }

    @Override
    public Boolean reservationAlreadyExists(DrugReservation drugReservation) throws Exception {
        List<DrugReservation> all = this.drugReservationRepository.findAll();
        boolean exists = false;
        for (DrugReservation dr : all) {
            if ((!dr.isDeleted()) && (dr.getPatient() == drugReservation.getPatient()) &&
                    (dr.getPharmacy() == drugReservation.getPharmacy()) &&
                    (dr.getDrug() == drugReservation.getDrug()) &&
                    (dr.getDateOfReservation().isAfter(drugReservation.getDateOfReservation().minusDays(5)))) {
                exists = true;
                break;
            }
        }

        return exists;
    }

    @Override
    public void sendEmail(DrugReservation dr) throws Exception {
        String to = this.userService.getPatientFromPrincipal().getEmail();
        String body = "You successfully made drug reservation.\n\nReservation number: ";
        body = body + dr.getReservationNumber().toString() + "\nDrug: " + dr.getDrug().getName();
        body = body + "\nPharmacy: " + dr.getPharmacy().getName() + "\nDate of reservation: ";
        body = body + dr.getDateOfReservation().toString() + "\nDate of picking up drug: ";
        body = body + dr.getTakingDrugDate().toString();
        String topic= "Drug reservation";
        this.emailService.sendEmail(to, body, topic);
    }

    @Override
    public boolean canBeCanceled(DrugReservation reservation) {
        boolean cancel = true;
        LocalDate oneDayBeforeReservation = reservation.getTakingDrugDate().minusDays(1);
        if (LocalDate.now().isAfter(oneDayBeforeReservation)) {
            cancel = false;
        }

        return cancel;
    }

    @Override
    public List<DrugReservation> findAllByDeleted(boolean deleted) {
        return this.drugReservationRepository.findAllByDeleted(deleted);
    }
}
