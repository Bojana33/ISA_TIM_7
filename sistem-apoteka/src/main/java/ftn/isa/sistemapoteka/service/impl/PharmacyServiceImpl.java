package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.repository.PharmacyRepository;
import ftn.isa.sistemapoteka.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private PharmacyRepository pharmacyRepository;

    @Autowired
    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository) {
        this.pharmacyRepository = pharmacyRepository;
    }

    @Override
    public Pharmacy save(Pharmacy pharmacy) throws Exception {
        if (this.pharmacyRepository.findByName(pharmacy.getName()) != null) {
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

    @Override
    public Map<Pharmacy, Double> findByDrug(Drug drug) {
        List<Pharmacy> pharmacies = findAll();
        Map<Pharmacy, Double> showList = new HashMap<>();
        for (Pharmacy pharmacy : pharmacies) {
            if (pharmacy.getDrugsQuantity().containsKey(drug.getCode()) && pharmacy.getDrugsQuantity().get(drug.getCode()) > 0) {
                showList.put(pharmacy, pharmacy.getDrugs().get(drug.getCode()));
            }
        }
        return showList;
    }

    @Override
    public List<Pharmacy> findByDrugReservationOrConsultationsOrAppointments(DrugReservation drugReservation, Consultation consultation, Appointment appointment) {
        return this.pharmacyRepository.findDistinctByDrugReservationsOrConsultationsOrAppointments(drugReservation, consultation, appointment);
    }

    @Override
    public List<Pharmacy> findByAppointments(Appointment appointment) {
        return this.pharmacyRepository.findDistinctByAppointments(appointment);
    }

    @Override
    public List<Pharmacy> findByConsultations(Consultation consultation) {
        return this.pharmacyRepository.findDistinctByConsultations(consultation);
    }

    @Override
    public List<Pharmacy> findByDrugReservations(DrugReservation drugReservation) {
        return this.pharmacyRepository.findDistinctByDrugReservations(drugReservation);
    }

    @Override
    public List<Pharmacy> findByDrugReservationsOrConsultations(DrugReservation drugReservation, Consultation consultation) {
        return this.pharmacyRepository.findDistinctByDrugReservationsOrConsultations(drugReservation, consultation);
    }

    @Override
    public List<Pharmacy> findByDrugReservationsOrAppointments(DrugReservation drugReservation, Appointment appointment) {
        return this.pharmacyRepository.findDistinctByDrugReservationsOrAppointments(drugReservation, appointment);
    }

    @Override
    public List<Pharmacy> findByConsultationsOrAppointments(Consultation consultation, Appointment appointment) {
        return this.pharmacyRepository.findDistinctByConsultationsOrAppointments(consultation, appointment);
    }

    @Override
    public List<Pharmacy> findByPatientDrugs(List<Long> drugs) {
        List<Pharmacy> pharmacies = this.pharmacyRepository.findAll();
        List<Pharmacy> toShow = new ArrayList<>();
        List<Pharmacy> toShowFinal = new ArrayList<>();
        for (Pharmacy pharmacy : pharmacies) {
                var a = pharmacy.getDrugsQuantity().keySet();

                Set<Long> b = new HashSet<Long>();
                for (var x : drugs)
                    b.add(x);
                var c = a.containsAll(b);

                if (c) {
                    toShow.add(pharmacy);
                }
        }
        for (Pharmacy pharmacy: toShow){
            int hasNone = 0;
            for (Long code: drugs){
                for (Map.Entry<Long,Integer> map: pharmacy.getDrugsQuantity().entrySet()){
                    if (map.getKey().equals(code)){
                        if (map.getValue() == 0){
                            hasNone += 1;
                        }
                    }
                }
            }
            if (hasNone == 0){
                toShowFinal.add(pharmacy);
            }
        }
        return toShowFinal;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Pharmacy update(Pharmacy pharmacy) {
        Pharmacy p = this.pharmacyRepository.getById(pharmacy.getId());
        p.setSubscriptionedPatients(pharmacy.getSubscriptionedPatients());
        p.setAddress(pharmacy.getAddress());
        p.setAppointments(pharmacy.getAppointments());
        p.setConsultations(pharmacy.getConsultations());
        p.setDrugsQuantity(pharmacy.getDrugsQuantity());
        p.setAverageRating(pharmacy.getAverageRating());
        p.setDermatologists(pharmacy.getDermatologists());
        p.setDrugReservations(pharmacy.getDrugReservations());
        p.setDrugs(pharmacy.getDrugs());
        p.setERecipes(pharmacy.getERecipes());
        p.setRatings(pharmacy.getRatings());
        p.setName(pharmacy.getName());
        p.setPharmacyAdministrators(pharmacy.getPharmacyAdministrators());

        return this.pharmacyRepository.save(p);
    }

    @Override
    public List<Pharmacy> sortByNameAsc() {
        return this.pharmacyRepository.findByOrderByNameAsc();
    }

    @Override
    public List<Pharmacy> sortByAddressAsc() {
        return this.pharmacyRepository.findByOrderByAddressAsc();
    }

    @Override
    public List<Pharmacy> sortByAverageRatingAsc() {
        return this.pharmacyRepository.findByOrderByAverageRatingAsc();
    }


    @Override
    public List<Pharmacy> findPharmaciesByDrugReservationsConsultationsAppointments(List<DrugReservation> drugReservations, List<Consultation> consultations, List<Appointment> appointments) {
        List<Pharmacy> pharmacies = new ArrayList<>();
        if (drugReservations.size() == 0 && consultations.size() == 0 && appointments.size() != 0) {
            for (Appointment appointment : appointments) {
                List<Pharmacy> pharmaciesList = findByAppointments(appointment);
                for (Pharmacy pharmacy : pharmaciesList) {
                    if (!pharmacies.contains(pharmacy)) {
                        pharmacies.add(pharmacy);
                    }
                }
            }
        } else if (drugReservations.size() == 0 && consultations.size() != 0 && appointments.size() == 0) {
            for (Consultation consultation : consultations) {
                List<Pharmacy> pharmaciesList = findByConsultations(consultation);
                for (Pharmacy pharmacy : pharmaciesList) {
                    if (!pharmacies.contains(pharmacy)) {
                        pharmacies.add(pharmacy);
                    }
                }
            }
        } else if (drugReservations.size() != 0 && consultations.size() == 0 && appointments.size() == 0) {
            for (DrugReservation drugReservation : drugReservations) {
                List<Pharmacy> pharmaciesList = findByDrugReservations(drugReservation);
                for (Pharmacy pharmacy : pharmaciesList) {
                    if (!pharmacies.contains(pharmacy)) {
                        pharmacies.add(pharmacy);
                    }
                }
            }
        } else if (drugReservations.size() != 0 && consultations.size() != 0 && appointments.size() == 0) {
            for (DrugReservation drugReservation : drugReservations) {
                for (Consultation consultation : consultations) {
                    List<Pharmacy> pharmaciesList = findByDrugReservationsOrConsultations(drugReservation, consultation);
                    for (Pharmacy pharmacy : pharmaciesList) {
                        if (!pharmacies.contains(pharmacy)) {
                            pharmacies.add(pharmacy);
                        }
                    }
                }
            }
        } else if (drugReservations.size() != 0 && consultations.size() == 0 && appointments.size() != 0) {
            for (DrugReservation drugReservation : drugReservations) {
                for (Appointment appointment : appointments) {
                    List<Pharmacy> pharmaciesList = findByDrugReservationsOrAppointments(drugReservation, appointment);
                    for (Pharmacy pharmacy : pharmaciesList) {
                        if (!pharmacies.contains(pharmacy)) {
                            pharmacies.add(pharmacy);
                        }
                    }
                }
            }
        } else if (drugReservations.size() == 0 && consultations.size() != 0 && appointments.size() != 0) {
            for (Consultation consultation : consultations) {
                for (Appointment appointment : appointments) {
                    List<Pharmacy> pharmaciesList = findByConsultationsOrAppointments(consultation, appointment);
                    for (Pharmacy pharmacy : pharmaciesList) {
                        if (!pharmacies.contains(pharmacy)) {
                            pharmacies.add(pharmacy);
                        }
                    }
                }
            }
        } else {
            for (DrugReservation drugReservation : drugReservations) {
                for (Consultation consultation : consultations) {
                    for (Appointment appointment : appointments) {
                        List<Pharmacy> pharmaciesList = findByDrugReservationOrConsultationsOrAppointments(drugReservation, consultation, appointment);
                        for (Pharmacy pharmacy : pharmaciesList) {
                            if (!pharmacies.contains(pharmacy)) {
                                pharmacies.add(pharmacy);
                            }
                        }
                    }
                }
            }
        }

        return pharmacies;
    }


}
