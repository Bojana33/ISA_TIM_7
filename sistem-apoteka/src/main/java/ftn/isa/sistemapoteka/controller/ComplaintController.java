package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("complaints")
public class ComplaintController {

    private ComplaintService complaintService;

    @Autowired
    public ComplaintController(ComplaintService complaintService){
        this.complaintService = complaintService;
    }
}
