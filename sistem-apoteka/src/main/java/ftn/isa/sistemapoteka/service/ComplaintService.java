package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Complaint;

import java.util.List;

public interface ComplaintService {

    List<Complaint> findAllComplaints();

    Complaint saveComplaint(Complaint complaint);

    Complaint updateComplaint(Complaint complaint);

    Complaint findById(Long id) throws Exception;

    void sendResponse(Complaint complaint);
}
