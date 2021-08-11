package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.repository.DrugRepository;
import ftn.isa.sistemapoteka.repository.PharmacyRepository;
import ftn.isa.sistemapoteka.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class DrugServiceImpl implements DrugService {

    private final DrugRepository drugRepository;

    private PharmacyRepository pharmacyRepository;

    @Autowired
    public DrugServiceImpl(DrugRepository drugRepository){
        this.drugRepository = drugRepository;
    }

    @Override
    public Collection<Drug> findAllDrugs() {
        return drugRepository.findAll();
    }

    @Override
    public Drug saveDrug(Drug drug) {
        this.drugRepository.save(drug);
        return drug;
    }

    @Override
    public Drug findByCode(Long code) {
        return this.drugRepository.findByCode(code);
    }

    @Override
    public void deleteDrug(Long id) {
        Drug d = this.drugRepository.getById(id);
        if(d.getReserved())
        {
            throw new IllegalStateException("Drug is reserved");
        }
        else
        {
            this.drugRepository.deleteById(id);
        }
    }

    @Override
    public void removeFromPharmacy(Long drugId, Long pharmacyId) {
        Drug drug = this.drugRepository.getById(drugId);//Dobavlja lek koji cemo ukloniti
        if(drug.getReserved())//Ako je lek rezervisan ne uklanja ga iz apoteke
        {
            throw new IllegalStateException("Drug is reserved");
        }
        else
        {
            Pharmacy p = this.pharmacyRepository.getById(pharmacyId); //Dobavlja apoteku iz koje se uklanja lek
            Set<Drug> drugs = p.getDrugs();//Izmena liste lekova pri apoteci
            drugs.remove(drug);
            p.setDrugs(drugs);
            this.pharmacyRepository.save(p);//Cuvanje izmenjene liste
        }
    }

    @Override
    public List<Drug> findInPharmacy(String drugName, Long pharmacyId) {
        Pharmacy p = this.pharmacyRepository.getById(pharmacyId);
        List<Drug> drugs =  this.drugRepository.findAllByName(drugName);
        if(p.getDrugs().containsAll(drugs))
        {
            return drugs;
        }
        else {
            return (List<Drug>) p.getDrugs();
        }
    }

    @Override
    public boolean addToPharmacy(Drug drug, Long pharmacyId) {
        Pharmacy p = this.pharmacyRepository.getById(pharmacyId);
        Set<Drug> drugs = p.getDrugs();
        return drugs.add(drug);
    }

    @Override
    public Drug editDrug(Drug newDrug, Long drugId, Long pharmacyId) {
        Drug drug = this.drugRepository.getById(drugId);
        removeFromPharmacy(drugId, pharmacyId);
        drug.setDrugType(newDrug.getDrugType());
        drug.setReserved(newDrug.getReserved());
        drug.setStructure(newDrug.getStructure());
        drug.setDrugShape(newDrug.getDrugShape());
        drug.setProducer(newDrug.getProducer());
        drug.setName(newDrug.getName());
        drug.setOnPrescription(newDrug.getOnPrescription());
        drug.setContraindications(newDrug.getContraindications());
        drug.setAdditionalNote(newDrug.getAdditionalNote());
        drug.setCode(newDrug.getCode());
        drug.setDailyIntake(newDrug.getDailyIntake());
        drug.setReplacementDrugs(newDrug.getReplacementDrugs());
        drug.setLoyaltyPoints(newDrug.getLoyaltyPoints());
        drug.setQuantity(newDrug.getQuantity());
        drug.setPrice(newDrug.getPrice());
        addToPharmacy(drug, pharmacyId);
        return drug;
    }


}
