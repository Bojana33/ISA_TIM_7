package ftn.isa.sistemapoteka.Service.impl;

import ftn.isa.sistemapoteka.Repository.ComplaintRepository;
import ftn.isa.sistemapoteka.Service.ComplaintService;
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
