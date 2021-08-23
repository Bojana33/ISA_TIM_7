package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Days;
import ftn.isa.sistemapoteka.model.Pharmacist;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.model.User;
import ftn.isa.sistemapoteka.repository.ConsultationRepository;
import ftn.isa.sistemapoteka.repository.PharmacyRepository;
import ftn.isa.sistemapoteka.repository.UserRepository;
import ftn.isa.sistemapoteka.service.DaysService;
import ftn.isa.sistemapoteka.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    private ConsultationRepository consultationRepository;
    private UserRepository userRepository;
    private DaysService daysService;


    @Autowired
    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository) {
        this.pharmacyRepository = pharmacyRepository;
    }

    @Override
    public Pharmacy save(Pharmacy pharmacy) throws Exception {
        if (this.pharmacyRepository.findByName(pharmacy.getName()) != null){
            throw new Exception("Pharmacy with that name already exist!");
        }
        return this.pharmacyRepository.save(pharmacy);
    }

    @Override
    public Pharmacy findById(Long id) {
        return this.pharmacyRepository.getById(id);
    }

    @Override
    public List<Pharmacy> findAll() {
        return this.pharmacyRepository.findAll();
    }
}

    @Override
    public boolean removePharmacist(Pharmacy pharmacy, Pharmacist pharmacist) {
        boolean ret = false;
        if (this.consultationRepository.getConsultationsByPharmacist(pharmacist).size() > 0) {
            Set<Pharmacist> pharmacists = pharmacy.getPharmacists();
            ret = pharmacists.remove(pharmacist);
            pharmacy.setPharmacists(pharmacists);
        } else {
            throw new IllegalStateException("This pharmacist has a consultation pending");
        }
        return ret;
    }

    @Override
    public List<Pharmacist> findPharmacistByName(Pharmacy pharmacy, String name) {
        List<Pharmacist> pharmacists = null;
        List<User> users = userRepository.findUserByFirstNameContaining(name);
        for (Pharmacist pharmacist :
                pharmacy.getPharmacists()) {
            for (User user :
                    users) {
                if (pharmacist.getFirstName().equals(user.getFirstName()))
                    pharmacists.add(pharmacist);
            }
        }
        return pharmacists;
    }
    @Override
    public Pharmacist addPharmacist(Pharmacist pharmacist, Pharmacy pharmacy, LocalDate beggining, LocalDate end, LocalTime dayBeggining, LocalTime dayEnd) {

        Set<Pharmacist> pharmacists = pharmacy.getPharmacists();
        pharmacists.add(pharmacist);
        pharmacy.setPharmacists(pharmacists);

        Days d = new Days();
        d.setUser(pharmacist);
        d.setBeggining(beggining);
        d.setDayBeggining(dayBeggining);
        d.setDayEnd(dayEnd);
        d.setEnd(end);
        d.setVacation(false);
        d.setVacationApproved(false);
        d.setVacationSubmitted(false);
        daysService.saveDays(d);

        this.userRepository.save(pharmacist);
        this.pharmacyRepository.save(pharmacy);
        return pharmacist;
    }
    //todo izvestaj o poslovanju apoteke i graficki prikazi
}

