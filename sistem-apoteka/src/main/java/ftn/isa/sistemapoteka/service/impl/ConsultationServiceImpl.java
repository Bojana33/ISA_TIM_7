package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.repository.ConsultationRepository;
import ftn.isa.sistemapoteka.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private ConsultationRepository consultationRepository;

    @Autowired
    public ConsultationServiceImpl(ConsultationRepository consultationRepository){
        this.consultationRepository = consultationRepository;
    }
}
