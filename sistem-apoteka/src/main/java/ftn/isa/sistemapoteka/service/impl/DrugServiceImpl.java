package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.repository.DrugRepository;
import ftn.isa.sistemapoteka.service.DrugService;
import ftn.isa.sistemapoteka.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class DrugServiceImpl implements DrugService {

    private DrugRepository drugRepository;


    @Override
    public Collection<Drug> findAllDrugs() {
        return drugRepository.findAll();
    }

    @Override
    public Drug saveDrug(Drug drug) {
        Drug d = new Drug();
        d.setName(drug.getName());
        d.setCode(drug.getCode());
        d.setDrugType(drug.getDrugType());
        d.setContraindications(drug.getContraindications());
        d.setStructure(drug.getStructure());
        d.setDailyIntake(drug.getDailyIntake());
        this.drugRepository.save(d);
        return d;
    }

    public Drug save(Drug drug) throws Exception {
        if(this.drugRepository.findById(drug.getId()).isPresent()) {
            throw new Exception("Drug already exist(User Service)");
        }

        return this.drugRepository.save(drug);
    }

    @Override
    public Drug findByCode(Long code)  throws Exception{
        Drug drug = this.drugRepository.findByCode(code);
        if (drug == null) {
            throw new Exception("Drug with this code does not exist");
        }

        return drug;
    }

    @Override
    public List<Drug> findByName(String name) {
        return this.drugRepository.findByKeyword(name);
    }

    @Override
    public Page<Drug> findPaginated(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        return this.drugRepository.findAll(pageable);
    }

    @Override
    public Drug updatePatientsWithAllergies(Drug drug, Patient patient) throws Exception {
        Drug forUpdate = findByCode(drug.getCode());

        Set<Patient> patients = forUpdate.getPatientsWithAllergies();
        patients.add(patient);
        forUpdate.setPatientsWithAllergies(patients);

        this.drugRepository.save(forUpdate);

        return forUpdate;
    }

    @Override
    public Drug findById(Long id) throws Exception {
        if (!this.drugRepository.findById(id).isPresent()) { throw new Exception("No such value(drug service)"); }

        return this.drugRepository.findById(id).get();
    }

    @Override
    public void decrementQuantity(Long drugId) throws Exception {
        Drug forUpdate = findById(drugId);
        int newQuantity = forUpdate.getQuantity()-1;
        forUpdate.setQuantity(newQuantity);

        this.drugRepository.save(forUpdate);
    }
}
