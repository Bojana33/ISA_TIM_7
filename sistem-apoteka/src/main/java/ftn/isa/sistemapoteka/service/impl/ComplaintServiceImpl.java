package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.repository.ComplaintRepository;
import ftn.isa.sistemapoteka.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private ComplaintRepository complaintRepository;

    @Autowired
    public ComplaintServiceImpl(ComplaintRepository complaintRepository){
        this.complaintRepository = complaintRepository;
    }
}
