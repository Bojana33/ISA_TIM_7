package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Complaint;
import ftn.isa.sistemapoteka.repository.ComplaintRepository;
import ftn.isa.sistemapoteka.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private ComplaintRepository complaintRepository;

    @Autowired
    public ComplaintServiceImpl(ComplaintRepository complaintRepository){
        this.complaintRepository = complaintRepository;
    }

    @Override
    public List<Complaint> findAllComplaints() {
        return this.complaintRepository.findAll();
    }

    @Override
    public Complaint saveComplaint(Complaint complaint) {
        Complaint c = new Complaint();
        c.setText(complaint.getText());
        c.setPatient(complaint.getPatient());
        c.setComplaintType(complaint.getComplaintType());
        c.setPharmacyId(complaint.getPharmacyId());
        c.setIsAnswered(complaint.getIsAnswered());
        c.setResponse(complaint.getResponse());
        c.setPharmacistId(complaint.getPharmacistId());
        c.setDermatologistId(complaint.getDermatologistId());
        return this.complaintRepository.save(c);
    }

    @Override
    public Complaint updateComplaint(Complaint complaint) {
        Complaint c = this.complaintRepository.getOne(complaint.getId());
        c.setText(complaint.getText());
        c.setPatient(complaint.getPatient());
        c.setComplaintType(complaint.getComplaintType());
        c.setPharmacyId(complaint.getPharmacyId());
        c.setIsAnswered(complaint.getIsAnswered());
        c.setResponse(complaint.getResponse());
        c.setPharmacistId(complaint.getPharmacistId());
        c.setDermatologistId(complaint.getDermatologistId());
        return this.complaintRepository.save(c);
    }

    @Override
    public Complaint findById(Long id) throws Exception {
        if (this.complaintRepository.getOne(id) == null){
            throw new Exception("Complaint with this id doesn't exist");
        }
        return this.complaintRepository.getOne(id);
    }
}
