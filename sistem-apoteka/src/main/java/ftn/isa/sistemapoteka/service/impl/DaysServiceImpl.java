package ftn.isa.sistemapoteka.service.impl;


import ftn.isa.sistemapoteka.model.Days;
import ftn.isa.sistemapoteka.repository.DaysRepository;
import ftn.isa.sistemapoteka.service.DaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DaysServiceImpl implements DaysService {
    private final DaysRepository daysRepository;

    @Autowired
    public DaysServiceImpl(DaysRepository daysRepository){
        this.daysRepository = daysRepository;
    }


    @Override
    public Days saveDays(Days days) {
        this.daysRepository.save(days);
        return days;
    }
}
