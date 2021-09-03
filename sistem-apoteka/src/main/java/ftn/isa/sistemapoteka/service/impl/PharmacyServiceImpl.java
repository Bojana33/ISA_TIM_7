package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Appointment;
import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.Pharmacist;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.repository.PharmacyRepository;
import ftn.isa.sistemapoteka.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    @Autowired
    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository){
        this.pharmacyRepository = pharmacyRepository;
    }

    @Override
    public Pharmacy save(Pharmacy pharmacy) throws Exception {
        return this.pharmacyRepository.save(pharmacy);
    }

    @Override
    public Pharmacy updateAppointments(Pharmacy pharmacy) throws Exception {
        if (!this.pharmacyRepository.findById(pharmacy.getId()).isPresent()) {
            throw new Exception("No value (Pharmacy service)");
        }
        Pharmacy forUpdate = this.pharmacyRepository.findById(pharmacy.getId()).get();

        forUpdate.setAppointments(pharmacy.getAppointments());
        this.pharmacyRepository.save(forUpdate);
        return forUpdate;
    }

    @Override
    public Pharmacy findById(Long id) throws Exception {
        if (this.pharmacyRepository.getById(id) == null) {
            throw new Exception("Pharmacy does not exist");
        }
        return this.pharmacyRepository.getById(id);
    }

    @Override
    public List<Pharmacy> findAll() {
        return this.pharmacyRepository.findAll();
    }

    @Override
    public List<Pharmacy> findByKeyword(String keyword) {
        return this.pharmacyRepository.findByKeyword(keyword);
    }

    @Override
    public List<Pharmacy> orderByNameAsc() { return this.pharmacyRepository.findByOrderByNameAsc(); }

    @Override
    public List<Pharmacy> orderByNameDesc() { return this.pharmacyRepository.findByOrderByNameDesc(); }

    @Override
    public List<Pharmacy> orderByRatingAsc() { return this.pharmacyRepository.findByOrderByAverageRatingAsc(); }

    @Override
    public List<Pharmacy> orderByRatingDesc() { return this.pharmacyRepository.findByOrderByAverageRatingDesc(); }

    @Override
    public List<Pharmacy> findAllThatContainsDrug(Drug drug) {
        List<Pharmacy> pharmacies = this.pharmacyRepository.findAll();
        List<Pharmacy> withDrug = new ArrayList<>();

        for (Pharmacy pharmacy : pharmacies) {
            for (Drug d : pharmacy.getDrugs()) {
                if (Objects.equals(d.getName(), drug.getName())) {
                    withDrug.add(pharmacy);
                }
            }
        }

        return withDrug;
    }

    @Override
    public Pharmacy findByName(String phName) throws Exception {
        Pharmacy pharmacy = this.pharmacyRepository.findByName(phName);
        if (pharmacy == null) { throw new Exception("Pharmacy with this name already exist"); }
        return pharmacy;
    }

    @Override
    public List<Pharmacy> findWithAvailablePharmacists(LocalDateTime ldt) {
        List<Pharmacy> all = this.pharmacyRepository.findAll();
        Set<Pharmacy> filtered = new HashSet<>();

        for (Pharmacy ph: all) {
            // prodji kroz farmaceute
            for (Pharmacist p : ph.getPharmacists()) {
                // proveri da li je slobodan
                // bilo gde da je zauzet ne moze
                // ako nema appointmenta onda je slobodan
                if (p.getAppointments().size() == 0) {
                    filtered.add(ph);
                    break;
                }
                for (Appointment ap : p.getAppointments()) {
                    // proveri da li je predstojeci
                    if (ap.getStartingTime().isAfter(LocalDateTime.now())) {
                        // proveri da li ima takvog termina
                        LocalDateTime halfAnHourBefore = ap.getStartingTime().minusMinutes(30);
                        LocalDateTime apEnd = ap.getStartingTime().plusMinutes(ap.getDurationInMinutes().intValue());
                        if ((ldt.isBefore(halfAnHourBefore)) || (ldt.isAfter(apEnd))) {
                            filtered.add(ph);
                        } else if (!(ldt.isBefore(halfAnHourBefore)) ||(ldt.isAfter(apEnd))) {
                            if (ap.getDeleted()) {
                                filtered.add(ph);
                            }
                        } else if ((ldt.isBefore(halfAnHourBefore)) || !(ldt.isAfter(apEnd))) {
                            if (ap.getDeleted()) {
                                filtered.add(ph);
                            }
                        }
                    }
                }
            }
        }
        List<Pharmacy> f = new ArrayList<>(filtered);
        return f;
    }
}
