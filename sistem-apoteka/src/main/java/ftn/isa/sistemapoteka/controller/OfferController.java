package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("offers")
public class OfferController {

    private OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService){
        this.offerService = offerService;
    }
}
