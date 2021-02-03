package ftn.isa.sistemapoteka.Service.impl;

import ftn.isa.sistemapoteka.Repository.ConsultationRepository;
import ftn.isa.sistemapoteka.Service.ConsultationService;
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
