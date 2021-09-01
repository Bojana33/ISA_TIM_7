package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.*;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    Pharmacy getById(Long id);
    Pharmacy findByName(String name);

    Set<Pharmacy> findAllBySubscriptionedPatients(Patient patient);

    List<Pharmacy> findDistinctByDrugReservationsOrConsultationsOrAppointments(DrugReservation drugReservation, Consultation consultation, Appointment appointment);

    List<Pharmacy> findDistinctByAppointments(Appointment appointment);

    List<Pharmacy> findDistinctByConsultations(Consultation consultation);

    List<Pharmacy> findDistinctByDrugReservations(DrugReservation drugReservation);

    List<Pharmacy> findDistinctByDrugReservationsOrAppointments(DrugReservation drugReservation, Appointment appointment);

    List<Pharmacy> findDistinctByDrugReservationsOrConsultations(DrugReservation drugReservation, Consultation consultation);

    List<Pharmacy> findDistinctByConsultationsOrAppointments(Consultation consultation, Appointment appointment);
}
