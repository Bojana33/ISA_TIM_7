package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Drug;
import ftn.isa.sistemapoteka.model.Patient;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface DrugService {
    Collection<Drug> findAllDrugs();
    Drug saveDrug(Drug drug);
    Drug save(Drug drug) throws Exception;
    Drug findByCode(Long code) throws Exception;

    List<Drug> findByName(String name);
    Page<Drug> findPaginated(int pageNum, int pageSize);

    Drug updatePatientsWithAllergies(Drug drug, Patient patient) throws Exception;

    Drug findById(Long id) throws Exception;

    void decrementQuantity(Long drugId) throws Exception;
    void incrementQuantity(Long drugId) throws Exception;
}
