package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Drug;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface DrugService {
    Collection<Drug> findAllDrugs();
    Drug saveDrug(Drug drug);
    Drug findByCode(Long code);

    List<Drug> findByName(String name);
    Page<Drug> findPaginated(int pageNum, int pageSize);
}
