package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;
}
